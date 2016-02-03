package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class CustomerQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<CustomerQueryBuilder, Customer, CustomerQuery, CustomerQueryModel, CustomerExpansionModel<Customer>> {

    private CustomerQueryBuilder(final CustomerQuery template) {
        super(template);
    }

    public static CustomerQueryBuilder of() {
        return new CustomerQueryBuilder(CustomerQuery.of());
    }

    @Override
    protected CustomerQueryBuilder getThis() {
        return this;
    }

    @Override
    public CustomerQuery build() {
        return super.build();
    }

    @Override
    public CustomerQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public CustomerQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomerQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public CustomerQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomerQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public CustomerQueryBuilder plusExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPathContainer<Customer>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public CustomerQueryBuilder plusPredicates(final Function<CustomerQueryModel, QueryPredicate<Customer>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public CustomerQueryBuilder plusPredicates(final QueryPredicate<Customer> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public CustomerQueryBuilder plusPredicates(final List<QueryPredicate<Customer>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public CustomerQueryBuilder plusSort(final Function<CustomerQueryModel, QuerySort<Customer>> m) {
        return super.plusSort(m);
    }

    @Override
    public CustomerQueryBuilder plusSort(final List<QuerySort<Customer>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomerQueryBuilder plusSort(final QuerySort<Customer> sort) {
        return super.plusSort(sort);
    }

    @Override
    public CustomerQueryBuilder predicates(final Function<CustomerQueryModel, QueryPredicate<Customer>> m) {
        return super.predicates(m);
    }

    @Override
    public CustomerQueryBuilder predicates(final QueryPredicate<Customer> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public CustomerQueryBuilder predicates(final List<QueryPredicate<Customer>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public CustomerQueryBuilder sort(final Function<CustomerQueryModel, QuerySort<Customer>> m) {
        return super.sort(m);
    }

    @Override
    public CustomerQueryBuilder sort(final List<QuerySort<Customer>> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomerQueryBuilder sort(final QuerySort<Customer> sort) {
        return super.sort(sort);
    }

    @Override
    public CustomerQueryBuilder sortMulti(final Function<CustomerQueryModel, List<QuerySort<Customer>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public CustomerQueryBuilder expansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPathContainer<Customer>> m) {
        return super.expansionPaths(m);
    }
}
