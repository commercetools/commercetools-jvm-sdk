package io.sphere.sdk.customergroups.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public class CustomerGroupQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CustomerGroupQueryBuilder, CustomerGroup, CustomerGroupQuery, CustomerGroupQueryModel, CustomerGroupExpansionModel<CustomerGroup>> {

    private CustomerGroupQueryBuilder(final CustomerGroupQuery template) {
        super(template);
    }

    public static CustomerGroupQueryBuilder of() {
        return new CustomerGroupQueryBuilder(CustomerGroupQuery.of());
    }

    @Override
    protected CustomerGroupQueryBuilder getThis() {
        return this;
    }

    @Override
    public CustomerGroupQuery build() {
        return super.build();
    }

    @Override
    public CustomerGroupQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CustomerGroupQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomerGroupQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomerGroupQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomerGroupQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomerGroupQueryBuilder plusExpansionPaths(final Function<CustomerGroupExpansionModel<CustomerGroup>, ExpansionPathContainer<CustomerGroup>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CustomerGroupQueryBuilder plusPredicates(final Function<CustomerGroupQueryModel, QueryPredicate<CustomerGroup>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CustomerGroupQueryBuilder plusPredicates(final QueryPredicate<CustomerGroup> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CustomerGroupQueryBuilder plusPredicates(final List<QueryPredicate<CustomerGroup>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CustomerGroupQueryBuilder plusSort(final Function<CustomerGroupQueryModel, QuerySort<CustomerGroup>> m) {
        return super.plusSort(m);
    }

    @Override
    public CustomerGroupQueryBuilder plusSort(final List<QuerySort<CustomerGroup>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomerGroupQueryBuilder plusSort(final QuerySort<CustomerGroup> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomerGroupQueryBuilder predicates(final Function<CustomerGroupQueryModel, QueryPredicate<CustomerGroup>> m) {
        return super.predicates(m);
    }

    @Override
    public CustomerGroupQueryBuilder predicates(final QueryPredicate<CustomerGroup> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CustomerGroupQueryBuilder predicates(final List<QueryPredicate<CustomerGroup>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CustomerGroupQueryBuilder sort(final Function<CustomerGroupQueryModel, QuerySort<CustomerGroup>> m) {
        return super.sort(m);
    }

    @Override
    public CustomerGroupQueryBuilder sort(final List<QuerySort<CustomerGroup>> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomerGroupQueryBuilder sort(final QuerySort<CustomerGroup> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomerGroupQueryBuilder sortMulti(final Function<CustomerGroupQueryModel, List<QuerySort<CustomerGroup>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CustomerGroupQueryBuilder expansionPaths(final Function<CustomerGroupExpansionModel<CustomerGroup>, ExpansionPathContainer<CustomerGroup>> m) {
        return super.expansionPaths(m);
    }
}
