package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

public final class CustomerInStoreQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CustomerInStoreQueryBuilder, Customer, CustomerInStoreQuery, CustomerQueryModel, CustomerExpansionModel<Customer>> {

    private CustomerInStoreQueryBuilder(final CustomerInStoreQuery template) {
        super(template);
    }

    public static CustomerInStoreQueryBuilder of(final String storeKey) {
        return new CustomerInStoreQueryBuilder(CustomerInStoreQuery.of(storeKey));
    }

    @Override
    protected CustomerInStoreQueryBuilder getThis() {
        return this;
    }

    @Override
    public CustomerInStoreQuery build() {
        return super.build();
    }

    @Override
    public CustomerInStoreQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CustomerInStoreQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomerInStoreQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomerInStoreQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomerInStoreQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomerInStoreQueryBuilder plusExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPathContainer<Customer>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CustomerInStoreQueryBuilder plusPredicates(final Function<CustomerQueryModel, QueryPredicate<Customer>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CustomerInStoreQueryBuilder plusPredicates(final QueryPredicate<Customer> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CustomerInStoreQueryBuilder plusPredicates(final List<QueryPredicate<Customer>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CustomerInStoreQueryBuilder plusSort(final Function<CustomerQueryModel, QuerySort<Customer>> m) {
        return super.plusSort(m);
    }

    @Override
    public CustomerInStoreQueryBuilder plusSort(final List<QuerySort<Customer>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomerInStoreQueryBuilder plusSort(final QuerySort<Customer> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomerInStoreQueryBuilder predicates(final Function<CustomerQueryModel, QueryPredicate<Customer>> m) {
        return super.predicates(m);
    }

    @Override
    public CustomerInStoreQueryBuilder predicates(final QueryPredicate<Customer> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CustomerInStoreQueryBuilder predicates(final List<QueryPredicate<Customer>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CustomerInStoreQueryBuilder sort(final Function<CustomerQueryModel, QuerySort<Customer>> m) {
        return super.sort(m);
    }

    @Override
    public CustomerInStoreQueryBuilder sort(final List<QuerySort<Customer>> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomerInStoreQueryBuilder sort(final QuerySort<Customer> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomerInStoreQueryBuilder sortMulti(final Function<CustomerQueryModel, List<QuerySort<Customer>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CustomerInStoreQueryBuilder expansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPathContainer<Customer>> m) {
        return super.expansionPaths(m);
    }
    
}
