package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.ByIdFetchImpl;

public class CustomerByIdFetch extends ByIdFetchImpl<Customer> {
    private CustomerByIdFetch(final String id) {
        super(id, CustomersEndpoint.ENDPOINT);
    }

    public static CustomerByIdFetch of(final Identifiable<Customer> id) {
        return of(id.getId());
    }

    public static CustomerByIdFetch of(final String id) {
        return new CustomerByIdFetch(id);
    }

    public static CustomerExpansionModel<Customer> expansionPath() {
        return new CustomerExpansionModel<>();
    }
}
