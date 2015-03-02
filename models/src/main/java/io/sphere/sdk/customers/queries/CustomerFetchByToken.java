package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.queries.FetchImpl;
import io.sphere.sdk.http.UrlQueryBuilder;

import static io.sphere.sdk.customers.queries.CustomersEndpoint.ENDPOINT;

public class CustomerFetchByToken extends FetchImpl<Customer> {
    private final String token;

    private CustomerFetchByToken(final String token) {
        super(ENDPOINT, "");
        this.token = token;
    }

    public static CustomerFetchByToken of(final String token) {
        return new CustomerFetchByToken(token);
    }

    public static CustomerFetchByToken of(final CustomerToken token) {
        return of(token.getValue());
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        return super.additionalQueryParameters().add("token", token);
    }
}
