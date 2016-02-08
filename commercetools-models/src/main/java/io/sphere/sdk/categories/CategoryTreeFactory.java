package io.sphere.sdk.categories;

import io.sphere.sdk.models.LocalizedStringEntry;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

final class CategoryTreeFactory {

    private CategoryTreeFactory() {
    }

    public static CategoryTreeFactory of() {
        return new CategoryTreeFactory();
    }

    public CategoryTree create(final List<Category> allCategoriesAsFlatList) {
        final Predicate<Category> isRootCategory = c -> c.getParent() == null;
        final List<Category> roots = allCategoriesAsFlatList.parallelStream().filter(isRootCategory).collect(toList());
        final List<Category> allAsFlatList = allCategoriesAsFlatList;
        final Map<LocalizedStringEntry, Category> categoriesByLocaleAndSlug = new HashMap<>();
        allCategoriesAsFlatList.forEach(category -> {
            final Set<Locale> localesForTheCategory = category.getSlug().getLocales();
            localesForTheCategory.forEach(locale -> {
                final LocalizedStringEntry stringsEntry = LocalizedStringEntry.of(locale, category.getSlug().get(locale));
                categoriesByLocaleAndSlug.put(stringsEntry, category);
            });
        });
        final Map<String, Category> categoriesById = allCategoriesAsFlatList.stream().collect(toMap(Category::getId, Function.<Category>identity()));
        final Map<String, List<Category>> childrenByParentId = new HashMap<>();
        allCategoriesAsFlatList.forEach(category ->
            Optional.ofNullable(category.getParent()).ifPresent(parentReference -> {
                final String parentId = parentReference.getId();
                final List<Category> entries = childrenByParentId.getOrDefault(parentId, new LinkedList<>());
                entries.add(category);
                childrenByParentId.put(parentId, entries);
            })
        );
        return new CategoryTreeImpl(roots, allAsFlatList, categoriesByLocaleAndSlug, categoriesById, childrenByParentId);
    }
}
