package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.FetchImpl;

import static java.util.Arrays.asList;

public class CartByCustomerIdFetch extends FetchImpl<Cart> {

    private CartByCustomerIdFetch(final String customerId) {
        super(CartsEndpoint.ENDPOINT, "", asList(HttpQueryParameter.of("customerId", customerId)));
    }

    public static CartByCustomerIdFetch of(final Identifiable<Customer> customer) {
        return of(customer.getId());
    }

    public static CartByCustomerIdFetch of(final String customerId) {
        return new CartByCustomerIdFetch(customerId);
    }
}
