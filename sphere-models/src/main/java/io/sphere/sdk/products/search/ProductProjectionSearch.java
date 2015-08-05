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

    static ProductProjectionSearch ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    static ProductProjectionSearch ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    static ProductProjectionSearch of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionSearchImpl(productProjectionType);
    }

    @Override
    ProductProjectionSearch withText(final LocalizedStringEntry text);

    @Override
    ProductProjectionSearch withText(final Locale locale, final String text);

    @Override
    ProductProjectionSearch withFacets(final List<FacetExpression<ProductProjection>> facets);

    @Override
    ProductProjectionSearch withFacets(final FacetExpression<ProductProjection> facet);

    @Override
    ProductProjectionSearch withFacets(final Function<ProductProjectionSearchModel, FacetExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusFacets(final List<FacetExpression<ProductProjection>> facets);

    @Override
    ProductProjectionSearch plusFacets(final FacetExpression<ProductProjection> facet);

    @Override
    ProductProjectionSearch plusFacets(final Function<ProductProjectionSearchModel, FacetExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withResultFilters(final List<FilterExpression<ProductProjection>> resultFilters);

    @Override
    ProductProjectionSearch withResultFilters(final FilterExpression<ProductProjection> resultFilter);

    @Override
    ProductProjectionSearch withResultFilters(final Function<ProductProjectionSearchModel, FilterExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusResultFilters(final List<FilterExpression<ProductProjection>> resultFilters);

    @Override
    ProductProjectionSearch plusResultFilters(final FilterExpression<ProductProjection> resultFilter);

    @Override
    ProductProjectionSearch plusResultFilters(final Function<ProductProjectionSearchModel, FilterExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withQueryFilters(final List<FilterExpression<ProductProjection>> queryFilters);

    @Override
    ProductProjectionSearch withQueryFilters(final FilterExpression<ProductProjection> queryFilter);

    @Override
    ProductProjectionSearch withQueryFilters(final Function<ProductProjectionSearchModel, FilterExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusQueryFilters(final List<FilterExpression<ProductProjection>> queryFilters);

    @Override
    ProductProjectionSearch plusQueryFilters(final FilterExpression<ProductProjection> queryFilter);

    @Override
    ProductProjectionSearch plusQueryFilters(final Function<ProductProjectionSearchModel, FilterExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withFacetFilters(final List<FilterExpression<ProductProjection>> facetFilters);

    @Override
    ProductProjectionSearch withFacetFilters(final FilterExpression<ProductProjection> facetFilter);

    @Override
    ProductProjectionSearch withFacetFilters(final Function<ProductProjectionSearchModel, FilterExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch plusFacetFilters(final List<FilterExpression<ProductProjection>> facetFilters);

    @Override
    ProductProjectionSearch plusFacetFilters(final FilterExpression<ProductProjection> facetFilter);

    @Override
    ProductProjectionSearch plusFacetFilters(final Function<ProductProjectionSearchModel, FilterExpression<ProductProjection>> m);

    @Override
    ProductProjectionSearch withSort(final SearchSort<ProductProjection> sort);

    @Override
    ProductProjectionSearch withSort(final Function<ProductProjectionSearchModel, SearchSort<ProductProjection>> m);

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
