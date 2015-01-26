package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.FetchImpl;
import io.sphere.sdk.utils.UrlQueryBuilder;

public class CartFetchByCustomerId extends FetchImpl<Cart> {
    private final String customerId;

    private CartFetchByCustomerId(final String customerId) {
        super(CartsEndpoint.ENDPOINT, "");
        this.customerId = customerId;
    }

    @Override
    protected UrlQueryBuilder additionalQueryParameters() {
        return super.additionalQueryParameters().add("customerId", customerId);
    }

    public static CartFetchByCustomerId of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    public static CartFetchByCustomerId of(final String customerId) {
        return new CartFetchByCustomerId(customerId);
    }
}
