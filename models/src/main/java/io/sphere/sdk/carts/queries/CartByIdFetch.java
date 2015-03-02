package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.ByIdFetchImpl;

/**
 Gets a cart by ID.

 {@include.example io.sphere.sdk.carts.queries.CartByIdFetchTest#fetchById()}
 */
public class CartByIdFetch extends ByIdFetchImpl<Cart> {
    private CartByIdFetch(final String id) {
        super(id, CartsEndpoint.ENDPOINT);
    }

    public static CartByIdFetch of(final String id) {
        return new CartByIdFetch(id);
    }
}
