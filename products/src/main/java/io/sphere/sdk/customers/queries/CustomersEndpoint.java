package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.http.JsonEndpoint;

final class CustomersEndpoint {
    static JsonEndpoint<Customer> ENDPOINT = JsonEndpoint.of(Customer.typeReference(), "/customers");
}
