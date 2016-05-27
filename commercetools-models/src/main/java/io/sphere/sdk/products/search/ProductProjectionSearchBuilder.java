package io.sphere.sdk.products.search;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.LocalizedStringEntry;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.search.*;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public final class ProductProjectionSearchBuilder extends ResourceMetaModelSearchDslBuilderImpl<ProductProjectionSearchBuilder, ProductProjection, ProductProjectionSearch, ProductProjectionSortSearchModel,
        ProductProjectionFilterSearchModel, ProductProjectionFacetSearchModel, ProductProjectionExpansionModel<ProductProjection>> {
    private ProductProjectionSearchBuilder(final ProductProjectionSearch delegate) {
        super(delegate);
    }

    public static ProductProjectionSearchBuilder ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    public static ProductProjectionSearchBuilder ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    public static ProductProjectionSearchBuilder of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionSearchBuilder(ProductProjectionSearch.of(productProjectionType));
    }

    @Override
    protected ProductProjectionSearchBuilder getThis() {
        return this;
    }

    @Override
    public ProductProjectionSearch build() {
        return super.build();
    }

    @Override
    public ProductProjectionSearchBuilder facetedSearch(final FacetedSearchExpression<ProductProjection> facetedSearchExpression) {
        return super.facetedSearch(facetedSearchExpression);
    }

    @Override
    public ProductProjectionSearchBuilder facetedSearch(final List<FacetedSearchExpression<ProductProjection>> facetedSearchExpressions) {
        return super.facetedSearch(facetedSearchExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder facets(final List<FacetExpression<ProductProjection>> facets) {
        return super.facets(facets);
    }

    @Override
    public ProductProjectionSearchBuilder fuzzy(final Boolean fuzzy) {
        return super.fuzzy(fuzzy);
    }

    @Override
    public ProductProjectionSearchBuilder fuzzyLevel(final Integer fuzzyLevel) {
        return super.fuzzyLevel(fuzzyLevel);
    }

    @Override
    public ProductProjectionSearchBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductProjectionSearchBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductProjectionSearchBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductProjectionSearchBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductProjectionSearchBuilder plusExpansionPaths(final ExpansionPath<ProductProjection> expansionPath) {
        return super.plusExpansionPaths(expansionPath);
    }

    @Override
    public ProductProjectionSearchBuilder plusExpansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths) {
        return super.plusExpansionPaths(expansionPaths);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacetedSearch(final FacetedSearchExpression<ProductProjection> facetedSearchExpression) {
        return super.plusFacetedSearch(facetedSearchExpression);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacetedSearch(final List<FacetedSearchExpression<ProductProjection>> facetedSearchExpressions) {
        return super.plusFacetedSearch(facetedSearchExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacetFilters(final List<FilterExpression<ProductProjection>> filterExpressions) {
        return super.plusFacetFilters(filterExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacetFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m) {
        return super.plusFacetFilters(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacets(final Function<ProductProjectionFacetSearchModel, FacetExpression<ProductProjection>> m) {
        return super.plusFacets(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusQueryFilters(final List<FilterExpression<ProductProjection>> filterExpressions) {
        return super.plusQueryFilters(filterExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder plusQueryFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m) {
        return super.plusQueryFilters(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusResultFilters(final List<FilterExpression<ProductProjection>> filterExpressions) {
        return super.plusResultFilters(filterExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder plusResultFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m) {
        return super.plusResultFilters(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusSort(final Function<ProductProjectionSortSearchModel, SortExpression<ProductProjection>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusSort(final SortExpression<ProductProjection> sortExpression) {
        return super.plusSort(sortExpression);
    }

    @Override
    public ProductProjectionSearchBuilder plusSort(final List<SortExpression<ProductProjection>> sortExpressions) {
        return super.plusSort(sortExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder queryFilters(final List<FilterExpression<ProductProjection>> filterExpressions) {
        return super.queryFilters(filterExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder resultFilters(final List<FilterExpression<ProductProjection>> filterExpressions) {
        return super.resultFilters(filterExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder text(final Locale locale, final String text) {
        return super.text(locale, text);
    }

    @Override
    public ProductProjectionSearchBuilder text(final LocalizedStringEntry text) {
        return super.text(text);
    }

    @Override
    public ProductProjectionSearchBuilder expansionPaths(final ExpansionPath<ProductProjection> expansionPath) {
        return super.expansionPaths(expansionPath);
    }

    @Override
    public ProductProjectionSearchBuilder expansionPaths(final List<ExpansionPath<ProductProjection>> expansionPaths) {
        return super.expansionPaths(expansionPaths);
    }

    @Override
    public ProductProjectionSearchBuilder expansionPaths(final ExpansionPathContainer<ProductProjection> holder) {
        return super.expansionPaths(holder);
    }

    @Override
    public ProductProjectionSearchBuilder expansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m) {
        return super.expansionPaths(m);
    }

    @Override
    public ProductProjectionSearchBuilder facetFilters(final List<FilterExpression<ProductProjection>> filterExpressions) {
        return super.facetFilters(filterExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder facetFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m) {
        return super.facetFilters(m);
    }

    @Override
    public ProductProjectionSearchBuilder facets(final FacetExpression<ProductProjection> facetExpression) {
        return super.facets(facetExpression);
    }

    @Override
    public ProductProjectionSearchBuilder facets(final Function<ProductProjectionFacetSearchModel, FacetExpression<ProductProjection>> m) {
        return super.facets(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusExpansionPaths(final ExpansionPathContainer<ProductProjection> holder) {
        return super.plusExpansionPaths(holder);
    }

    @Override
    public ProductProjectionSearchBuilder plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacets(final FacetExpression<ProductProjection> facetExpression) {
        return super.plusFacets(facetExpression);
    }

    @Override
    public ProductProjectionSearchBuilder plusFacets(final List<FacetExpression<ProductProjection>> facetExpressions) {
        return super.plusFacets(facetExpressions);
    }

    @Override
    public ProductProjectionSearchBuilder queryFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m) {
        return super.queryFilters(m);
    }

    @Override
    public ProductProjectionSearchBuilder resultFilters(final Function<ProductProjectionFilterSearchModel, List<FilterExpression<ProductProjection>>> m) {
        return super.resultFilters(m);
    }

    @Override
    public ProductProjectionSearchBuilder sort(final Function<ProductProjectionSortSearchModel, SortExpression<ProductProjection>> m) {
        return super.sort(m);
    }

    @Override
    public ProductProjectionSearchBuilder sort(final SortExpression<ProductProjection> sortExpression) {
        return super.sort(sortExpression);
    }

    @Override
    public ProductProjectionSearchBuilder sort(final List<SortExpression<ProductProjection>> sortExpressions) {
        return super.sort(sortExpressions);
    }
}
