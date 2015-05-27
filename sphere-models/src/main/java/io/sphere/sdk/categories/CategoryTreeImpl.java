package io.sphere.sdk.categories;

import io.sphere.sdk.models.Base;

import java.util.Optional;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.sphere.sdk.utils.MapUtils.*;
import static io.sphere.sdk.utils.ListUtils.*;

class CategoryTreeImpl extends Base implements CategoryTree {
    private final List<Category> roots;
    private final List<Category> allAsFlatList;
    final Map<LocaleSlugPair, Category> categoriesByLocaleAndSlug;
    final Map<String, Category> categoriesById;

    CategoryTreeImpl(final List<Category> roots,
                     final List<Category> allAsFlatList,
                     final Map<LocaleSlugPair, Category> categoriesByLocaleAndSlug,
                     final Map<String, Category> categoriesById) {
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
    public Optional<Category> getById(final String id) {
        return getOptional(categoriesById, id);
    }

    @Override
    public Optional<Category> getBySlug(final String slug, final Locale locale) {
        return getOptional(categoriesByLocaleAndSlug, new LocaleSlugPair(locale, slug));
    }

    @Override
    public List<Category> getAllAsFlatList() {
        return allAsFlatList;
    }

    @Override
    public void rebuildAsync() {

    }
}
