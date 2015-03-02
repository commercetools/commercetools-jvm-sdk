package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.queries.FetchImpl;
import io.sphere.sdk.http.UrlQueryBuilder;

import static io.sphere.sdk.customers.queries.CustomersEndpoint.ENDPOINT;

public class CustomerByTokenFetch extends FetchImpl<Customer> {
    private final String token;

    private CustomerByTokenFetch(final String token) {
        super(ENDPOINT, "");
        this.token = token;
    }

    public static CustomerByTokenFetch of(final String token) {
        return new CustomerByTokenFetch(token);
    }

    public static CustomerByTokenFetch of(final CustomerToken token) {
        return of(token.getValue());
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        return super.additionalQueryParameters().add("token", token);
    }
}
