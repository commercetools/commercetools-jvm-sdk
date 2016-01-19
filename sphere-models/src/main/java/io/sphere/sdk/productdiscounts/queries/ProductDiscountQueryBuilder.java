package io.sphere.sdk.productdiscounts.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.expansion.ProductDiscountExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public class ProductDiscountQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductDiscountQueryBuilder, ProductDiscount, ProductDiscountQuery, ProductDiscountQueryModel, ProductDiscountExpansionModel<ProductDiscount>> {

    private ProductDiscountQueryBuilder(final ProductDiscountQuery template) {
        super(template);
    }

    public static ProductDiscountQueryBuilder of() {
        return new ProductDiscountQueryBuilder(ProductDiscountQuery.of());
    }

    @Override
    protected ProductDiscountQueryBuilder getThis() {
        return this;
    }

    @Override
    public ProductDiscountQuery build() {
        return super.build();
    }

    @Override
    public ProductDiscountQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ProductDiscountQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductDiscountQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductDiscountQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductDiscountQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductDiscountQueryBuilder plusExpansionPaths(final Function<ProductDiscountExpansionModel<ProductDiscount>, ExpansionPathContainer<ProductDiscount>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductDiscountQueryBuilder plusPredicates(final Function<ProductDiscountQueryModel, QueryPredicate<ProductDiscount>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductDiscountQueryBuilder plusPredicates(final QueryPredicate<ProductDiscount> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductDiscountQueryBuilder plusPredicates(final List<QueryPredicate<ProductDiscount>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductDiscountQueryBuilder plusSort(final Function<ProductDiscountQueryModel, QuerySort<ProductDiscount>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductDiscountQueryBuilder plusSort(final List<QuerySort<ProductDiscount>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductDiscountQueryBuilder plusSort(final QuerySort<ProductDiscount> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductDiscountQueryBuilder predicates(final Function<ProductDiscountQueryModel, QueryPredicate<ProductDiscount>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductDiscountQueryBuilder predicates(final QueryPredicate<ProductDiscount> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductDiscountQueryBuilder predicates(final List<QueryPredicate<ProductDiscount>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductDiscountQueryBuilder sort(final Function<ProductDiscountQueryModel, QuerySort<ProductDiscount>> m) {
        return super.sort(m);
    }

    @Override
    public ProductDiscountQueryBuilder sort(final List<QuerySort<ProductDiscount>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductDiscountQueryBuilder sort(final QuerySort<ProductDiscount> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductDiscountQueryBuilder sortMulti(final Function<ProductDiscountQueryModel, List<QuerySort<ProductDiscount>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductDiscountQueryBuilder expansionPaths(final Function<ProductDiscountExpansionModel<ProductDiscount>, ExpansionPathContainer<ProductDiscount>> m) {
        return super.expansionPaths(m);
    }
}
