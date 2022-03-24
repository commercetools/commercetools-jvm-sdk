package io.sphere.sdk.productselections.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.productselections.ProductSelectionAssignment;


import java.util.List;
import java.util.function.Function;

public final class ProductSelectionAssignmentInStoreQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ProductSelectionAssignmentInStoreQueryBuilder, ProductSelectionAssignment, ProductSelectionAssignmentInStoreQuery, ProductSelectionAssignmentQueryModel, ProductSelectionAssignmentExpansionModel<ProductSelectionAssignment>> {

    private ProductSelectionAssignmentInStoreQueryBuilder(final ProductSelectionAssignmentInStoreQuery template) {
        super(template);
    }

    public static ProductSelectionAssignmentInStoreQueryBuilder of(final String storeKey) {
        return new ProductSelectionAssignmentInStoreQueryBuilder(ProductSelectionAssignmentInStoreQuery.of(storeKey));
    }

    @Override
    protected ProductSelectionAssignmentInStoreQueryBuilder getThis() {
        return this;
    }

    @Override
    public ProductSelectionAssignmentInStoreQuery build() {
        return super.build();
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusExpansionPaths(final Function<ProductSelectionAssignmentExpansionModel<ProductSelectionAssignment>, ExpansionPathContainer<ProductSelectionAssignment>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusPredicates(final Function<ProductSelectionAssignmentQueryModel, QueryPredicate<ProductSelectionAssignment>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusPredicates(final QueryPredicate<ProductSelectionAssignment> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusPredicates(final List<QueryPredicate<ProductSelectionAssignment>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusSort(final Function<ProductSelectionAssignmentQueryModel, QuerySort<ProductSelectionAssignment>> m) {
        return super.plusSort(m);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusSort(final List<QuerySort<ProductSelectionAssignment>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder plusSort(final QuerySort<ProductSelectionAssignment> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder predicates(final Function<ProductSelectionAssignmentQueryModel, QueryPredicate<ProductSelectionAssignment>> m) {
        return super.predicates(m);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder predicates(final QueryPredicate<ProductSelectionAssignment> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder predicates(final List<QueryPredicate<ProductSelectionAssignment>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder sort(final Function<ProductSelectionAssignmentQueryModel, QuerySort<ProductSelectionAssignment>> m) {
        return super.sort(m);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder sort(final List<QuerySort<ProductSelectionAssignment>> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder sort(final QuerySort<ProductSelectionAssignment> sort) {
        return super.sort(sort);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder sortMulti(final Function<ProductSelectionAssignmentQueryModel, List<QuerySort<ProductSelectionAssignment>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ProductSelectionAssignmentInStoreQueryBuilder expansionPaths(final Function<ProductSelectionAssignmentExpansionModel<ProductSelectionAssignment>, ExpansionPathContainer<ProductSelectionAssignment>> m) {
        return super.expansionPaths(m);
    }
    
}
