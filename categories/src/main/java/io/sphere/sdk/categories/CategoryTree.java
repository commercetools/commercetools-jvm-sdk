package io.sphere.sdk.categories;

import com.google.common.base.Optional;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * All categories in the project, represented as an in-memory tree.
 */
public interface CategoryTree {
    /**
     * Root categories (the ones that have no parent).
     */
    List<Category> getRoots();

    /**
     * Finds a category by id.
     */
    Optional<Category> getById(String id);

    /**
     * Finds a category by the slug and a specific locale.
     */
    Optional<Category> getBySlug(String slug, Locale locale);

    /**
     * All categories as a flat list.
     */
    List<Category> getAllAsFlatList();

    /**
     * Rebuilds the category cache for category tree implementations, if possible.
     */
    void rebuildAsync();
}

