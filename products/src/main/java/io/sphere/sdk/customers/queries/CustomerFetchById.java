package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.FetchByIdImpl;

public class CustomerFetchById extends FetchByIdImpl<Customer> {
    private CustomerFetchById(final String id) {
        super(id, CustomersEndpoint.ENDPOINT);
    }

    public static CustomerFetchById of(final String id) {
        return new CustomerFetchById(id);
    }
}
