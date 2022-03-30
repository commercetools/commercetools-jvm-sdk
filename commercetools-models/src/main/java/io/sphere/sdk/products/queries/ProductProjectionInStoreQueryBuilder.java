package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;


import java.util.List;
import java.util.function.Function;

public final class ProductProjectionInStoreQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductProjectionInStoreQueryBuilder, ProductProjection, ProductProjectionInStoreQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> {

    private ProductProjectionInStoreQueryBuilder(final ProductProjectionInStoreQuery template) {
        super(template);
    }

    public static ProductProjectionInStoreQueryBuilder of(final String storeKey) {
        return new ProductProjectionInStoreQueryBuilder(ProductProjectionInStoreQuery.of(storeKey));
    }

    @Override
    protected ProductProjectionInStoreQueryBuilder getThis() {
        return this;
    }

    @Override
    public ProductProjectionInStoreQuery build() {
        return super.build();
    }

    @Override
    public ProductProjectionInStoreQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusPredicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusPredicates(final QueryPredicate<ProductProjection> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusPredicates(final List<QueryPredicate<ProductProjection>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusSort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusSort(final List<QuerySort<ProductProjection>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder plusSort(final QuerySort<ProductProjection> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder predicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder predicates(final QueryPredicate<ProductProjection> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder predicates(final List<QueryPredicate<ProductProjection>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder sort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m) {
        return super.sort(m);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder sort(final List<QuerySort<ProductProjection>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder sort(final QuerySort<ProductProjection> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder sortMulti(final Function<ProductProjectionQueryModel, List<QuerySort<ProductProjection>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductProjectionInStoreQueryBuilder expansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m) {
        return super.expansionPaths(m);
    }
    
}
