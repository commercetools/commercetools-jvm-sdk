package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * Fetches a customer by a known ID.
 *
 * {@include.example io.sphere.sdk.customers.queries.CustomerByIdGetTest#execution()}
 */
public interface CustomerByIdGet extends MetaModelGetDsl<Customer, Customer, CustomerByIdGet, CustomerExpansionModel<Customer>> {
    static CustomerByIdGet of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    static CustomerByIdGet of(final String id) {
        return new CustomerByIdGetImpl(id);
    }

    @Override
    List<ExpansionPath<Customer>> expansionPaths();

    @Override
    CustomerByIdGet plusExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByIdGet withExpansionPaths(final ExpansionPath<Customer> expansionPath);

    @Override
    CustomerByIdGet withExpansionPaths(final List<ExpansionPath<Customer>> expansionPaths);
}
