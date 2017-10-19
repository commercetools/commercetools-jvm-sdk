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
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#demoGetRoots()}
     *
     * @return root categories
     */
    List<Category> getRoots();
                                 
    /**
     * Finds a category by id.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#demoFindByIdFound()}
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#demoFindByIdNotFound()}
     *
     * @param id the ID of the category to search for
     * @return category
     */
    Optional<Category> findById(String id);

    /**
     * Finds a category by its external ID.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#findByExternalId()}
     *
     * @param externalId the external id of the category to find
     * @return optional
     */
    Optional<Category> findByExternalId(String externalId);

    /**
     * Finds a category by the slug and a specific locale.
     * @param locale the locale
     * @param slug the slug
     * @return a category matching the criteria
     */
    Optional<Category> findBySlug(Locale locale, String slug);

    /**
     * Finds a category by the key.
     * @param key the category key
     * @return a category matching the criteria
     */
    Optional<Category> findByKey(String key);

    /**
     * All categories as a flat list.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#getAllAsFlatList()}
     *
     * @return all categories
     */
    List<Category> getAllAsFlatList();

    /**
     * return the children for category. If there are no children or category is not in this {@link CategoryTree} then the list is empty.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#findChildren()}
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#findChildrenLeaf()}
     *
     * @param category the category which should be the parent category to the result list
     * @return list of children or empty list
     */
    List<Category> findChildren(final Identifiable<Category> category);

    /**
     * Gets a list containing all categories that share a parent
     * with at least one of the given categories.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#getSiblings()}
     *
     *
     * @param categoryIds Categories for which the sibling categories should be fetched
     * @return a list of sibling categories
     */
    List<Category> findSiblings(final Collection<? extends Identifiable<Category>> categoryIds);

    /**
     * Gets the subtree of the given parent categories.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#getSubtreeFromRootCategory()}
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#getSubtree()}
     *
     * @param parentCategories the list of parent categories to use as a starting point
     * @return the subtree with the subcategories including the parent categories
     */
    CategoryTree getSubtree(final Collection<? extends Identifiable<Category>> parentCategories);

    /**
     * For a given category searches the ancestor that is in root level.
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#getRootAncestor()}
     *
     * @param category the category which to find the root ancestor
     * @return the root ancestor of the category or the same category in case it is a root itself
     */
    Category getRootAncestor(final Identifiable<Category> category);

    /**
     * For a subtree the categories which are at the top level.
     * <p>Unlike {@link #getRoots()} this does ignore the parent reference.</p>
     *
     * <p>The examples use the <a href="https://gist.github.com/schleichardt/7f0d023b815206cb2a57" target="_blank">ABC example set</a>.</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTreeTest#getSubtreeRoots()}
     *
     * @return subtree roots
     */
    List<Category> getSubtreeRoots();

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

