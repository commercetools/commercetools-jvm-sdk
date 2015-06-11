package io.sphere.sdk.customers.commands;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.client.JsonEndpoint;

final class CustomerEndpoint {
    static final JsonEndpoint<Customer> ENDPOINT = JsonEndpoint.of(Customer.typeReference(), "/customers");
    static final JsonEndpoint<CustomerSignInResult> ENDPOINT_SIGNIN_RESULT = JsonEndpoint.of(CustomerSignInResult.typeReference(), ENDPOINT.endpoint());
}
