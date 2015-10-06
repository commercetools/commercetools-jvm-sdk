package io.sphere.sdk.products.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.search.*;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * Searches for products.
 *
 * Consult the documentation for <a href="{@docRoot}/io/sphere/sdk/meta/ProductSearchDocumentation.html">Product Search API</a> for more information.
 */
public interface ProductProjectionSearch extends MetaModelSearchDsl<ProductProjection, ProductProjectionSearch, ProductProjectionSearchModel, ProductProjectionExpansionModel<ProductProjection>> {

    static TypeReference<PagedSearchResult<ProductProjection>> resultTypeReference() {
        return new TypeReference<PagedSearchResult<ProductProjection>>(){
            @Override
            public String toString() {
                return "TypeReference<PagedSearchResult<ProductProjection>>";
            }
        };
    }

    /**
     * Creates a search request object for the staged data of a product.
     * @return search request for staged data
     */
    static ProductProjectionSearch ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    /**
     * Creates a search request object for the current data of a product.
     * @return search request for current data
     */
    static ProductProjectionSearch ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    /**
     * Creates a search request object with the product projection type specified by {@code productProjectionType}.
     * @param productProjectionType the desired projection type for the search results
     * @see #ofStaged()
     * @see #ofCurrent()
     * @return search request for current data
     */
    static ProductProjectionSearch of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionSearchImpl(productProjectionType);
    }

    @Override
    ProductProjectionSearch withText(final LocalizedStringEntry text);

    @Override
    ProductProjectionSearch withText(final Locale locale, final String text);

    @Override
    ProductProjectionSearch withFacets(final List<FacetExpression<ProductProjection>> facetExpressions);

    @Override
    ProductProjectionSearch withFacets(final FacetExpression<ProductProjection> facetExpression);

    @Override
    ProductProjectionSearch withFacets(final Function<ProductProjectionSearchModel, FacetExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusFacets(final List<FacetExpression<ProductProjection>> facetExpressions);

    @Override
    ProductProjectionSearch plusFacets(final FacetExpression<ProductProjection> facetExpression);

    @Override
    ProductProjectionSearch plusFacets(final Function<ProductProjectionSearchModel, FacetExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withResultFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch withResultFilters(final Function<ProductProjectionSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusResultFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusResultFilters(final Function<ProductProjectionSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch withQueryFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch withQueryFilters(final Function<ProductProjectionSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusQueryFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusQueryFilters(final Function<ProductProjectionSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch withFacetFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch withFacetFilters(final Function<ProductProjectionSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusFacetFilters(final List<FilterExpression<ProductProjection>> filterExpressions);

    @Override
    ProductProjectionSearch plusFacetFilters(final Function<ProductProjectionSearchModel, List<FilterExpression<ProductProjection>>> m);

    @Override
    ProductProjectionSearch plusFacetedSearch(final FacetedSearchExpression<ProductProjection> facetedSearchExpression);

    @Override
    ProductProjectionSearch plusFacetedSearch(final Function<ProductProjectionSearchModel, FacetedSearchExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withSort(final List<SortExpression<ProductProjection>> sortExpressions);

    @Override
    ProductProjectionSearch withSort(final SortExpression<ProductProjection> sortExpression);

    @Override
    ProductProjectionSearch withSort(final Function<ProductProjectionSearchModel, SortExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusSort(final List<SortExpression<ProductProjection>> sortExpressions);

    @Override
    ProductProjectionSearch plusSort(final SortExpression<ProductProjection> sortExpression);

    @Override
    ProductProjectionSearch plusSort(final Function<ProductProjectionSearchModel, SortExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withLimit(final long limit);

    @Override
    ProductProjectionSearch withOffset(final long offset);

    @Override
    ProductProjectionSearch withExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);

    @Override
    ProductProjectionSearch withExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionSearch withExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPath<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths);

    @Override
    ProductProjectionSearch plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath);

    @Override
    ProductProjectionSearch plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPath<ProductProjection>> m);

}
