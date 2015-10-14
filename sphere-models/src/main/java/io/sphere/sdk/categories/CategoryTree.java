package io.sphere.sdk.categories;

import io.sphere.sdk.models.Identifiable;

import java.util.*;

import static java.util.Objects.requireNonNull;

/**
 * All categories in the project, represented as an in-memory tree.
 *
 * <p>Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/CategoryDocumentation.html#category-tree">categories</a> for more information.</p>
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
    Optional<Category> findById(String id);

    Optional<Category> findByExternalId(String externalId);

    /**
     * Finds a category by the slug and a specific locale.
     * @param locale the locale
     * @param slug the slug
     * @return a category matching the criteria
     */
    Optional<Category> findBySlug(Locale locale, String slug);

    /**
     * All categories as a flat list.
     * @return all categories
     */
    List<Category> getAllAsFlatList();

    /**
     * return the children for category. If there are no children or category is not in this {@link CategoryTree} then the list is empty.
     * @param category the category which should be the parent category to the result list
     * @return list of children or empty list
     */
    List<Category> findChildren(final Identifiable<Category> category);

    /**
     * Creates a category tree from a flat list of categories.
     *
     * @param allCategoriesAsFlatList all categories as flat list.
     * @return the created category tree.
     */
    static CategoryTree of(final List<Category> allCategoriesAsFlatList) {
        requireNonNull(allCategoriesAsFlatList);
        return CategoryTreeFactory.of().create(allCategoriesAsFlatList);
    }
}

