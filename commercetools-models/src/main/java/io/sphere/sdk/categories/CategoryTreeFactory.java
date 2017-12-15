package io.sphere.sdk.categories;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static io.sphere.sdk.categories.CategoryTreeUtils.getCategoryOrThrow;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

final class CategoryTreeFactory {

    private CategoryTreeFactory() {
    }

    public static CategoryTreeFactory of() {
        return new CategoryTreeFactory();
    }

    public CategoryTree create(final List<Category> allCategoriesAsFlatList) {
        final List<Category> roots = findRoots(allCategoriesAsFlatList);
        return create(allCategoriesAsFlatList, roots, roots);
    }

    public CategoryTree create(final List<Category> allCategoriesAsFlatList, final List<Category> roots, final List<Category> subtreeRoots) {
        final List<Category> allAsFlatList = allCategoriesAsFlatList;
        final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug = getLocalizedStringEntryCategoryMap(allCategoriesAsFlatList);
        final Map<String, Category> categoriesById = allCategoriesAsFlatList.stream().collect(toMap(Category::getId, Function.<Category>identity()));
        final Map<String, Category> categoriesByKey = allCategoriesAsFlatList.stream().filter(category -> category.getKey() != null).collect(toMap(Category::getKey, Function.<Category>identity()));
        final Map<String, List<Category>> childrenByParentId = getChildrenByParentIdMap(allCategoriesAsFlatList);
        return new CategoryTreeImpl(roots, allAsFlatList, categoriesByLocaleAndSlug, categoriesById, categoriesByKey ,childrenByParentId, subtreeRoots);
    }

    private Map<String, List<Category>> getChildrenByParentIdMap(final List<Category> allCategoriesAsFlatList) {
        final Map<String, List<Category>> childrenByParentId = new HashMap<>();
        allCategoriesAsFlatList.forEach(category ->
            Optional.ofNullable(category.getParent()).ifPresent(parentReference -> {
                final String parentId = parentReference.getId();
                final List<Category> entries = childrenByParentId.getOrDefault(parentId, new LinkedList<>());
                entries.add(category);
                childrenByParentId.put(parentId, entries);
            })
        );
        return childrenByParentId;
    }

    private Map<LocalizedStringEntry, Category> getLocalizedStringEntryCategoryMap(final List<Category> allCategoriesAsFlatList) {
        final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug = new HashMap<>();
        allCategoriesAsFlatList.forEach(category -> {
            final Set<Locale> localesForTheCategory = category.getSlug().getLocales();
            localesForTheCategory.forEach(locale -> {
                final LocalizedStringEntry stringsEntry = LocalizedStringEntry.of(locale, category.getSlug().get(locale));
                categoriesByLocaleAndSlug.put(stringsEntry, category);
            });
        });
        return categoriesByLocaleAndSlug;
    }

    private List<Category> findRoots(final List<Category> allCategoriesAsFlatList) {
        final Predicate<Category> isRootCategory = c -> c.getParent() == null;
        return allCategoriesAsFlatList.parallelStream().filter(isRootCategory).collect(toList());
    }

    private List<Category> getSubtreeAsFlatList(final CategoryTree categoryTree, final Collection<? extends Identifiable<Category>> parentCategories) {
        final List<Category> categories = new ArrayList<>();
        parentCategories.forEach(parent -> {
            categories.add(getCategoryOrThrow(parent, categoryTree));
            final List<Category> children = categoryTree.findChildren(parent);
            categories.addAll(getSubtreeAsFlatList(categoryTree, children));
        });
        return categories;
    }

    public CategoryTree createSubtree(final CategoryTree categoryTree, final Collection<? extends Identifiable<Category>> subtreeRoots) {
        final List<Category> subtreeRootsCategories = subtreeRoots.stream()
                .map(identifiable -> getCategoryOrThrow(identifiable, categoryTree))
                .collect(toList());

        final List<String> subtreeRootIds = subtreeRoots.parallelStream().map(Identifiable::getId).collect(toList());

        subtreeRootsCategories.forEach(subtreeRoot -> {
            final Optional<String> rootAncestorOptional = subtreeRoot.getAncestors()
                    .stream()
                    .map(x -> x.getId())
                    .filter(ancestorId -> subtreeRootIds.contains(ancestorId))
                    .findFirst();
            if (rootAncestorOptional.isPresent()) {
                throw new IllegalArgumentException(String.format("category of ID [%s] cannot be subtree root and descendant of [%s]", subtreeRoot.getId(), rootAncestorOptional.get()));
            }
        });

        final List<Category> includedCategories = new LinkedList<>();
        subtreeRootsCategories.forEach(parentCategory -> {
            includedCategories.add(parentCategory);
            final List<Category> children = categoryTree.findChildren(parentCategory);
            includedCategories.addAll(getSubtreeAsFlatList(categoryTree, children));
        });
        final List<Category> roots = findRoots(includedCategories);
        return create(includedCategories, roots, subtreeRootsCategories);
    }
}
