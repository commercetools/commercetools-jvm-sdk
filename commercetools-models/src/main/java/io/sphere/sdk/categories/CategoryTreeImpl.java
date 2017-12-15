package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.*;

import static io.sphere.sdk.utils.SphereInternalUtils.immutableCopyOf;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;

class CategoryTreeImpl extends Base implements CategoryTree {
    private final List<Category> roots;
    private final List<Category> subtreeRoots;
    private final List<Category> allAsFlatList;
    private final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug;
    private final Map<String, Category> categoriesById;
    private final Map<String, Category> categoriesByKey;
    private final Map<String, List<Category>> childrenByParentId;

    CategoryTreeImpl(final List<Category> roots,
                     final List<Category> allAsFlatList,
                     final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug,
                     final Map<String, Category> categoriesById,
                     final Map<String, Category> categoriesByKey,
                     final Map<String, List<Category>> childrenByParentId, final List<Category> subtreeRoots) {
        this.childrenByParentId = childrenByParentId;
        this.roots = immutableCopyOf(roots);
        this.subtreeRoots = roots == subtreeRoots ? this.roots : immutableCopyOf(subtreeRoots);
        this.allAsFlatList = immutableCopyOf(allAsFlatList);
        this.categoriesByLocaleAndSlug = immutableCopyOf(categoriesByLocaleAndSlug);
        this.categoriesById = immutableCopyOf(categoriesById);
        this.categoriesByKey = immutableCopyOf(categoriesByKey);
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
    public Optional<Category> findByKey(String key) {
        return Optional.ofNullable(categoriesByKey.get(key));
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
        final Category theCategory = CategoryTreeUtils.getCategoryOrThrow(category, this);
        return theCategory
                .getAncestors().stream().findFirst()
                .flatMap(root -> findById(root.getId()))
                .orElse(theCategory);
    }

    @Override
    public List<Category> findSiblings(final Collection<? extends Identifiable<Category>> categoryIdentifiables) {
        return categoryIdentifiables.stream()
                .flatMap(category -> getSiblings(CategoryTreeUtils.getCategoryOrThrow(category, this)).stream())
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
        requireNonNull(parentCategories);
        return CategoryTreeFactory.of().createSubtree(this, parentCategories);
    }

    @Override
    public List<Category> getSubtreeRoots() {
        return subtreeRoots;
    }
}
