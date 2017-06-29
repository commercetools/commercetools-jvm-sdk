package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.ExistsAndMissingFilterSearchModelSupport;
import io.sphere.sdk.search.model.TermFilterSearchModel;

import java.util.List;

/**
 * Model to build term filters for the category ID.
 *
 * <p>The code example use the following tree for explanation</p>
 *
 * <pre><code>{@include.file commercetools-models/src/test/resources/CategoryTree/categoriesAsAscii.txt}</code></pre>
 *
 * @param <T> type of the resource
 */
public interface ProductCategoriesIdTermFilterSearchModel<T>
        extends ExistsAndMissingFilterSearchModelSupport<T>, TermFilterSearchModel<T, String> {
    /**
     * <p>Creates filters for a product which needs to be in all categories directly ({@code categoryIds}). Does not include the child categories.</p>
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#containsAll()}
     *
     * @param categoryIds the ID of the category which the products belongs to excluding subcategories
     * @return filter expressions
     */
    @Override
    List<FilterExpression<T>> containsAll(final Iterable<String> categoryIds);

    /**
     * <p>Creates filters for a product which needs to be in any of the given categories directly ({@code categoryIds}). Does not include the child categories.</p>
     * Alias for {@link #isIn(Iterable)}.
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#containsAny()}
     *
     * @param categoryIds the IDs of categories which the product should belong to at least to one of them
     * @return filter expressions
     * @see #isIn(Iterable)
     */
    @Override
    List<FilterExpression<T>> containsAny(final Iterable<String> categoryIds);

    /**
     * <p>Creates filters for a product which should directly belong to the given category in {@code categoryId}.</p>
     *
     * {@include.example io.sphere.sdk.products.search.CategoriesWithAncestorsIntegrationTest#isInSubtree()}
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#is()}
     *
     * @param categoryId the ID of the category which the products belong to including subcategories
     * @return filter expressions
     */
    @Override
    List<FilterExpression<T>> is(final String categoryId);

    /**
     * <p>Creates filters for a product which needs to be in any of the given categories directly ({@code categoryIds}). Does not include the child categories.</p>
     * Alias for {@link #containsAny(Iterable)}.
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#containsAny()}
     *
     * @param categoryIds the IDs of categories which the product should belong to at least to one of them
     * @return filter expressions
     * @see #containsAny(Iterable)
     */
    @Override
    List<FilterExpression<T>> isIn(final Iterable<String> categoryIds);

    /**
     * <p>Creates filters for a product which needs to be in any of the given categories or its descendants.</p>
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#containsAnyIncludingSubtrees()}
     *
     * @param categoryIds the IDs of categories which the product should belong to at least to one of them
     * @return filter expressions
     * @see #isIn(Iterable)
     */
    List<FilterExpression<T>> containsAnyIncludingSubtrees(final Iterable<String> categoryIds);

    /**
     * <p>Creates filters for a product which needs to be in all categories or its descendants.</p>
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#containsAllIncludingSubtrees()}
     *
     * @param categoryIds the ID of the category which the products belongs to including subcategories
     * @return filter expressions
     */
    List<FilterExpression<T>> containsAllIncludingSubtrees(final Iterable<String> categoryIds);

    /**
     * <p>Creates filters for a product which is in a category and its descendants.</p>
     *
     * {@include.example io.sphere.sdk.products.search.CategoriesWithAncestorsIntegrationTest#isInSubtree()}
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#isInSubtree()}
     *
     * @param categoryId the ID of the category which the products belong to including subcategories
     * @return filter expressions
     */
    List<FilterExpression<T>> isInSubtree(final String categoryId);

    /**
     * <p>Creates filters for a product which is in certain category trees ({@code categoryIdsSubtree}) or direct categories ({@code}).</p>
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#isInSubtreeOrInCategory()}
     *
     * @param categoryIdsSubtree IDs of categories which the product belongs directly or indirectly to
     * @param categoryIdsDirectly IDs of categories which the product belongs directly to
     * @return
     */
    List<FilterExpression<T>> isInSubtreeOrInCategory(final Iterable<String> categoryIdsSubtree, final Iterable<String> categoryIdsDirectly);
}
