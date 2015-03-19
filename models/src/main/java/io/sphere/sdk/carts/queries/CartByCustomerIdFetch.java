package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.FetchImpl;
import io.sphere.sdk.http.UrlQueryBuilder;

public class CartByCustomerIdFetch extends FetchImpl<Cart> {
    private final String customerId;

    private CartByCustomerIdFetch(final String customerId) {
        super(CartsEndpoint.ENDPOINT, "");
        this.customerId = customerId;
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        return super.additionalQueryParameters().add("customerId", customerId);
    }

    public static CartByCustomerIdFetch of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    public static CartByCustomerIdFetch of(final String customerId) {
        return new CartByCustomerIdFetch(customerId);
    }
}
