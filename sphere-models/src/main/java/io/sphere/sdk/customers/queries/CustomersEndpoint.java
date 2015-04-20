package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.client.JsonEndpoint;

final class CustomersEndpoint {
    static final JsonEndpoint<Customer> ENDPOINT = JsonEndpoint.of(Customer.typeReference(), "/customers");
}
