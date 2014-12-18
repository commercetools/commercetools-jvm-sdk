package io.sphere.sdk.categories;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * All categories in the project, represented as an in-memory tree.
 */
public interface CategoryTree {
    /**
     * Root categories (the ones that have no parent).
     * @return root categories
     */
    List<Category> getRoots();

    /**
     * Finds a category by id.
     * @param id the ID of the category to search for
     * @return category
     */
    Optional<Category> getById(String id);

    /**
     * Finds a category by the slug and a specific locale.
     * @param slug the slug
     * @param locale the locale
     * @return a category matching the criteria
     */
    Optional<Category> getBySlug(String slug, Locale locale);

    /**
     * All categories as a flat list.
     * @return all categories
     */
    List<Category> getAllAsFlatList();

    /**
     * Rebuilds the category cache for category tree implementations, if possible.
     */
    void rebuildAsync();

    /**
     * Creates a category tree from a flat list of categories.
     *
     * @param allCategoriesAsFlatListWithoutChildrenSettings all categories as flat list, {@code element.getChildren()} must result in an empty list.
     * @return the created category tree.
     */
    static CategoryTree of(final List<Category> allCategoriesAsFlatListWithoutChildrenSettings) {
        requireNonNull(allCategoriesAsFlatListWithoutChildrenSettings);
        return CategoryTreeFactory.create(allCategoriesAsFlatListWithoutChildrenSettings);
    }
}

