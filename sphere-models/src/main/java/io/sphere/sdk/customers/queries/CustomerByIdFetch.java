package io.sphere.sdk.customers.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface CustomerByIdFetch extends MetaModelFetchDsl<Customer, CustomerByIdFetch, CustomerExpansionModel<Customer>> {
    static CustomerByIdFetch of(final Identifiable<Customer> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static CustomerByIdFetch of(final String id) {
        return new CustomerByIdFetchImpl(id);
    }
}
