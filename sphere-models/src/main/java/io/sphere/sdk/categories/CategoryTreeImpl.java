package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.models.LocalizedStringsEntry;

import java.util.*;

import static io.sphere.sdk.utils.MapUtils.*;
import static io.sphere.sdk.utils.ListUtils.*;

class CategoryTreeImpl extends Base implements CategoryTree {
    private final List<Category> roots;
    private final List<Category> allAsFlatList;
    final Map<LocalizedStringsEntry, Category> categoriesByLocaleAndSlug;
    final Map<String, Category> categoriesById;
    private final Map<String, List<Category>> childrenByParentId;

    CategoryTreeImpl(final List<Category> roots,
                     final List<Category> allAsFlatList,
                     final Map<LocalizedStringsEntry, Category> categoriesByLocaleAndSlug,
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
        return getOptional(categoriesById, id);
    }

    @Override
    public Optional<Category> findBySlug(final Locale locale, final String slug) {
        return getOptional(categoriesByLocaleAndSlug, LocalizedStringsEntry.of(locale, slug));
    }

    @Override
    public List<Category> getAllAsFlatList() {
        return allAsFlatList;
    }

    @Override
    public List<Category> findByParent(final Identifiable<Category> category) {
        final String categoryId = category.getId();
        return childrenByParentId.getOrDefault(categoryId, Collections.emptyList());
    }
}
