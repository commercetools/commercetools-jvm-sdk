package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

public interface CustomerByTokenFetch extends MetaModelFetchDsl<Customer, CustomerByTokenFetch, CustomerExpansionModel<Customer>> {
    static CustomerByTokenFetch of(final String token) {
        return new CustomerByTokenFetchImpl(token);
    }

    static CustomerByTokenFetch of(final CustomerToken token) {
        return of(token.getValue());
    }

    @Override
    CustomerByTokenFetch plusExpansionPath(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    CustomerByTokenFetch withExpansionPath(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByTokenFetch plusExpansionPath(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenFetch withExpansionPath(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenFetch withExpansionPath(final List<ExpansionPath<Customer>> expansionPaths);
}
