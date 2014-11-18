package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.queries.FetchImpl;
import io.sphere.sdk.utils.UrlQueryBuilder;

import static io.sphere.sdk.customers.queries.CustomersEndpoint.ENDPOINT;

public class CustomerFetchByToken extends FetchImpl<Customer> {
    private final String token;

    public CustomerFetchByToken(final String token) {
        super(ENDPOINT, "");
        this.token = token;
    }

    public CustomerFetchByToken(final CustomerToken token) {
        this(token.getValue());
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        return super.additionalQueryParameters().add("token", token);
    }
}
