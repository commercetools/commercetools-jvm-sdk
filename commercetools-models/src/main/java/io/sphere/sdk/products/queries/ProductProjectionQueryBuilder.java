package io.sphere.sdk.products.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class ProductProjectionQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductProjectionQueryBuilder, ProductProjection, ProductProjectionQuery, ProductProjectionQueryModel, ProductProjectionExpansionModel<ProductProjection>> {

    private ProductProjectionQueryBuilder(final ProductProjectionQuery template) {
        super(template);
    }

    public static ProductProjectionQueryBuilder ofStaged() {
        return of(ProductProjectionType.STAGED);
    }

    public static ProductProjectionQueryBuilder ofCurrent() {
        return of(ProductProjectionType.CURRENT);
    }

    public static ProductProjectionQueryBuilder of(final ProductProjectionType productProjectionType) {
        return new ProductProjectionQueryBuilder(ProductProjectionQuery.of(productProjectionType));
    }

    @Override
    protected ProductProjectionQueryBuilder getThis() {
        return this;
    }

    @Override
    public ProductProjectionQuery build() {
        return super.build();
    }

    @Override
    public ProductProjectionQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ProductProjectionQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductProjectionQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductProjectionQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductProjectionQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductProjectionQueryBuilder plusExpansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductProjectionQueryBuilder plusPredicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductProjectionQueryBuilder plusPredicates(final QueryPredicate<ProductProjection> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductProjectionQueryBuilder plusPredicates(final List<QueryPredicate<ProductProjection>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductProjectionQueryBuilder plusSort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductProjectionQueryBuilder plusSort(final List<QuerySort<ProductProjection>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductProjectionQueryBuilder plusSort(final QuerySort<ProductProjection> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductProjectionQueryBuilder predicates(final Function<ProductProjectionQueryModel, QueryPredicate<ProductProjection>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductProjectionQueryBuilder predicates(final QueryPredicate<ProductProjection> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductProjectionQueryBuilder predicates(final List<QueryPredicate<ProductProjection>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductProjectionQueryBuilder sort(final Function<ProductProjectionQueryModel, QuerySort<ProductProjection>> m) {
        return super.sort(m);
    }

    @Override
    public ProductProjectionQueryBuilder sort(final List<QuerySort<ProductProjection>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductProjectionQueryBuilder sort(final QuerySort<ProductProjection> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductProjectionQueryBuilder sortMulti(final Function<ProductProjectionQueryModel, List<QuerySort<ProductProjection>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductProjectionQueryBuilder expansionPaths(final Function<ProductProjectionExpansionModel<ProductProjection>, ExpansionPathContainer<ProductProjection>> m) {
        return super.expansionPaths(m);
    }
}
