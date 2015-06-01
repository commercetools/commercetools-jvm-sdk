package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.queries.FetchImpl;

import static io.sphere.sdk.customers.queries.CustomersEndpoint.ENDPOINT;
import static java.util.Arrays.asList;

public class CustomerByTokenFetch extends FetchImpl<Customer> {

    private CustomerByTokenFetch(final String token) {
        super(ENDPOINT, "", asList(HttpQueryParameter.of("token", token)));
    }

    public static CustomerByTokenFetch of(final String token) {
        return new CustomerByTokenFetch(token);
    }

    public static CustomerByTokenFetch of(final CustomerToken token) {
        return of(token.getValue());
    }
}
