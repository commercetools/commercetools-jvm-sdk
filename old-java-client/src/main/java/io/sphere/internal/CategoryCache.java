package io.sphere.internal;

import com.google.common.collect.*;
import io.sphere.client.shop.model.Category;

import java.util.*;

public class CategoryCache {
    private final ImmutableList<Category> roots;
    private final ImmutableList<Category> all;
    private final ImmutableMap<String, Category> byIdMap;
    private final ImmutableMap<LocaleSlugPair, Category> categoriesByLocaleAndSlug;
    private final Locale defaultLocale;


    private CategoryCache(
            final ImmutableList<Category> roots,
            final ImmutableList<Category> all,
            final ImmutableMap<String, Category> categoriesById,
            final ImmutableMap<LocaleSlugPair, Category> categoriesByLocaleAndSlug,
            final Locale defaultLocale) {
        this.roots = roots;
        this.byIdMap = categoriesById;
        this.all = all;
        this.categoriesByLocaleAndSlug = categoriesByLocaleAndSlug;
        this.defaultLocale = defaultLocale;
    }

    /** Caches category tree in multiple different ways for fast lookup. */
    public static CategoryCache create(final Iterable<Category> roots, final Locale locale) {
        List<Category> all = getAllRecursive(roots);
        return new CategoryCache(ImmutableList.copyOf(roots), sortByName(all, locale), buildByIdMap(all), buildBySlugMap(all), locale);
    }

    public List<Category> getRoots() { return roots; }
    public Category getById(String id) { return byIdMap.get(id); }
    public Category getBySlug(String slug) {
        return getBySlug(slug, defaultLocale);
    }
    public Category getBySlug(final String slug, final Locale locale) {
        return categoriesByLocaleAndSlug.get(new LocaleSlugPair(locale, slug));
    }
    public List<Category> getAsFlatList() { return all; }

    // --------------------------------------------------
    // Helpers for create()
    // --------------------------------------------------

    private static List<Category> getAllRecursive(Iterable<Category> categories) {
        List<Category> result = new ArrayList<Category>();
        for (Category c: categories) {
            result.add(c);
            for (Category child: getAllRecursive(c.getChildren())) {
                result.add(child);
            }
        }
        return result;
    }

    private static ImmutableMap<String, Category> buildByIdMap(Collection<Category> categories) {
        Map<String, Category> map = new HashMap<String, Category>(categories.size());
        for (Category c: categories) {
            map.put(c.getId(), c);
        }
        return ImmutableMap.copyOf(map);
    }

    private static ImmutableMap<LocaleSlugPair, Category> buildBySlugMap(final Collection<Category> categories) {
        final Map<LocaleSlugPair, Category> map = Maps.newHashMap();
        for (final Category category : categories) {
            for (final Locale locale : category.getLocalizedSlug().getLocales()) {
                map.put(new LocaleSlugPair(locale, category.getSlug(locale)), category);
            }
        }
        return ImmutableMap.copyOf(map);
    }


    private static ImmutableList<Category> sortByName(Collection<Category> categories, Locale locale) {
        List<Category> categoriesCopy = new ArrayList<Category>(categories);
        Collections.sort(categoriesCopy, new ByNameComparator(locale));
        return ImmutableList.copyOf(categoriesCopy);
    }

    private static class LocaleSlugPair extends Pair<Locale, String> {
        public LocaleSlugPair(final Locale x, final String y) {
            super(x, y);
        }
    }

    private static class ByNameComparator implements Comparator<Category> {
        private final Locale locale;

        private ByNameComparator(Locale locale) { this.locale = locale; }

        @Override public int compare(Category category, Category other) {
            if (category == null && other == null) return 0;
            if (category == null && other != null) return -1;
            if (category != null && other == null) return 1;
            return category.getName(locale).compareTo(other.getName(locale));
        }

        /** Indicates whether some other object is equal to this comparator. */
        @Override public boolean equals(Object other) {
            return this == other;
        }
    }
}
