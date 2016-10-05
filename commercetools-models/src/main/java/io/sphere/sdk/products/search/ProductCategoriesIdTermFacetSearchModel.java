package io.sphere.sdk.products.search;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.model.TermFacetSearchModel;

/**
 *
 * Model to build term facets for the category ID.
 *
 * <p>The code example use the following tree for explanation</p>
 *
 * <pre><code>{@include.file commercetools-models/src/test/resources/CategoryTree/categoriesAsAscii.txt}</code></pre>
 *
 * @param <T> type of the resource
 */
public interface ProductCategoriesIdTermFacetSearchModel<T> extends TermFacetSearchModel<T, String> {

    @Override
    FilteredFacetExpression<T> onlyTerm(final String value);

    @Override
    FilteredFacetExpression<T> onlyTerm(final Iterable<String> values);

    @Override
    ProductCategoriesIdTermFacetSearchModel<T> withAlias(final String alias);

    @Override
    ProductCategoriesIdTermFacetSearchModel<T> withCountingProducts(final Boolean isCountingProducts);

    FilteredFacetExpression<T> onlyTermSubtree(final String categoryId);

    /**
     *
     * {@include.example io.sphere.sdk.products.search.ProductCategoriesIdTermFilterSearchModelIntegrationTest#facet()}
     *
     * @param categoryIds the IDs of the category the procuct belongs to directly or through a child
     * @return facet expression
     */
    FilteredFacetExpression<T> onlyTermSubtree(final Iterable<String> categoryIds);
}
