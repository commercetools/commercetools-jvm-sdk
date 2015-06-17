package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface CustomerByIdFetch extends MetaModelFetchDsl<Customer, CustomerByIdFetch, CustomerExpansionModel<Customer>> {
    static CustomerByIdFetch of(final Identifiable<Customer> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CustomerByIdFetch of(final String id) {
        return new CustomerByIdFetchImpl(id);
    }

    @Override
    CustomerByIdFetch plusExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    CustomerByIdFetch withExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByIdFetch plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByIdFetch withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByIdFetch withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
