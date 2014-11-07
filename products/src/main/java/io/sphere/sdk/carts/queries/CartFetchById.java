package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.FetchByIdImpl;

/**
 Gets a cart by ID.

 {@include.example io.sphere.sdk.carts.CartIntegrationTest#fetchById()}
 */
public class CartFetchById extends FetchByIdImpl<Cart> {
    public CartFetchById(final Identifiable<Cart> identifiable) {
        super(identifiable, CartsEndpoint.ENDPOINT);
    }
}
