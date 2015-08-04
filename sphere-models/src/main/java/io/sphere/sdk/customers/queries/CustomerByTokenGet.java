package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

public interface CustomerByTokenGet extends MetaModelGetDsl<Customer, Customer, CustomerByTokenGet, CustomerExpansionModel<Customer>> {
    static CustomerByTokenGet of(final String token) {
        return new CustomerByTokenGetImpl(token);
    }

    static CustomerByTokenGet of(final CustomerToken token) {
        return of(token.getValue());
    }

    @Override
    CustomerByTokenGet plusExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    CustomerByTokenGet withExpansionPaths(final Function<CustomerExpansionModel<Customer>, ExpansionPath<Customer>> m);

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByTokenGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByTokenGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
