package io.sphere.sdk.categories;


import java.util.Optional;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static io.sphere.sdk.utils.MapUtils.*;

class CategoryTreeImpl implements CategoryTree {
    private final ImmutableList<Category> roots;
    private final ImmutableList<Category> allAsFlatList;
    final Map<LocaleSlugPair, Category> categoriesByLocaleAndSlug;
    final Map<String, Category> categoriesById;

    CategoryTreeImpl(final List<Category> roots,
                     final List<Category> allAsFlatList,
                     final Map<LocaleSlugPair, Category> categoriesByLocaleAndSlug,
                     final Map<String, Category> categoriesById) {
        this.roots = ImmutableList.copyOf(roots);
        this.allAsFlatList = ImmutableList.copyOf(allAsFlatList);
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
