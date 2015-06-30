package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
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
    CustomerByTokenFetch plusExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    CustomerByTokenFetch withExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByTokenFetch plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenFetch withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenFetch withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
