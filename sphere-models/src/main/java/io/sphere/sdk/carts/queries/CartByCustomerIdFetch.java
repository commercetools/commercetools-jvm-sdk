package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.expansion.CartExpansionModel;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;

public interface CartByCustomerIdFetch extends MetaModelFetchDsl<Cart, CartByCustomerIdFetch, CartExpansionModel<Cart>> {
    static CartByCustomerIdFetch of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    static CartByCustomerIdFetch of(final String customerId) {
        return new CartByCustomerIdFetchImpl(customerId);
    }
}
