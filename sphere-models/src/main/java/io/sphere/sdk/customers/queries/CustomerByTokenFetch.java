package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerToken;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface CustomerByTokenFetch extends MetaModelFetchDsl<Customer, CustomerByTokenFetch, CustomerExpansionModel<Customer>> {
    static CustomerByTokenFetch of(final String token) {
        return new CustomerByTokenFetchImpl(token);
    }

    static CustomerByTokenFetch of(final CustomerToken token) {
        return of(token.getValue());
    }
}
