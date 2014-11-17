package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.FetchByIdImpl;

public class CustomerFetchById extends FetchByIdImpl<Customer> {
    private CustomerFetchById(final Identifiable<Customer> customer) {
        super(customer, CustomersEndpoint.ENDPOINT);
    }

    public static CustomerFetchById of(final Identifiable<Customer> customer) {
        return new CustomerFetchById(customer);
    }
}
