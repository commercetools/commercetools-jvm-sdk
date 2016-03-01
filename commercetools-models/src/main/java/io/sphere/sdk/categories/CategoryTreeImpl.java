package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.*;

import static io.sphere.sdk.utils.SphereInternalUtils.*;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

class CategoryTreeImpl extends Base implements CategoryTree {
    private final List<Category> roots;
    private final List<Category> allAsFlatList;
    final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug;
    final Map<String, Category> categoriesById;
    private final Map<String, List<Category>> childrenByParentId;

    CategoryTreeImpl(final List<Category> roots,
                     final List<Category> allAsFlatList,
                     final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug,
                     final Map<String, Category> categoriesById,
                     final Map<String, List<Category>> childrenByParentId) {
        this.childrenByParentId = childrenByParentId;
        this.roots = immutableCopyOf(roots);
        this.allAsFlatList = immutableCopyOf(allAsFlatList);
        this.categoriesByLocaleAndSlug = immutableCopyOf(categoriesByLocaleAndSlug);
        this.categoriesById = immutableCopyOf(categoriesById);
    }

    @Override
    public List<Category> getRoots() {
        return roots;
    }

    @Override
    public Optional<Category> findById(final String id) {
        return Optional.ofNullable(categoriesById.get(id));
    }

    @Override
    public Optional<Category> findByExternalId(final String externalId) {
        return getAllAsFlatList().parallelStream()
                .filter(cat -> Optional.ofNullable(cat.getExternalId()).map(extIdElement -> extIdElement.equals(externalId)).orElse(false))
                .findAny();//should be okay, since the externalId should be unique
    }

    @Override
    public Optional<Category> findBySlug(final Locale locale, final String slug) {
        return Optional.ofNullable(categoriesByLocaleAndSlug.get(LocalizedStringEntry.of(locale, slug)));
    }

    @Override
    public List<Category> getAllAsFlatList() {
        return allAsFlatList;
    }

    @Override
    public List<Category> findChildren(final Identifiable<Category> category) {
        final String categoryId = category.getId();
        return childrenByParentId.getOrDefault(categoryId, Collections.emptyList());
    }

    @Override
    public Category getRootAncestor(final Identifiable<Category> category) {
        requireNonNull(category);
        final Category theCategory = getCategoryOrThrow(category);
        return theCategory
                .getAncestors().stream().findFirst()
                .flatMap(root -> findById(root.getId()))
                .orElse(theCategory);
    }

    private Category getCategoryOrThrow(final Identifiable<Category> category) {
        return findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException(format("%s is not part of the category tree", category)));
    }

    @Override
    public List<Category> getSiblings(final Collection<? extends Identifiable<Category>> categoryIdentifiables) {
        return categoryIdentifiables.stream()
                .flatMap(category -> getSiblings(getCategoryOrThrow(category)).stream())
                .distinct()
                .filter(sibling -> !categoryIdentifiables.stream().anyMatch(c -> sibling.getId().equals(c.getId())))
                .collect(toList());
    }

    private List<Category> getSiblings(final Category category) {
        return Optional.ofNullable(category.getParent())
                .map(this::findChildren)
                .orElseGet(this::getRoots);
    }

    @Override
    public CategoryTree getSubtree(final Collection<? extends Identifiable<Category>> parentCategories) {
        return CategoryTree.of(getSubtreeAsFlatList(parentCategories));
    }

    private List<Category> getSubtreeAsFlatList(final Collection<? extends Identifiable<Category>> parentCategories) {
        final List<Category> categories = new ArrayList<>();
        parentCategories.stream().forEach(parent -> {
            categories.add(getCategoryOrThrow(parent));
            final List<Category> children = findChildren(parent);
            categories.addAll(getSubtreeAsFlatList(children));
        });
        return categories;
    }
}
