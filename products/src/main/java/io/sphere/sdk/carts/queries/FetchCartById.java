package io.sphere.sdk.carts.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.FetchImpl;

/**
 Gets a cart by ID.

 {@include.example io.sphere.sdk.carts.commands.CartIntegrationTest#fetchById()}
 */
public class FetchCartById extends FetchImpl<Cart> {
    public FetchCartById(final Identifiable<Cart> identifiable) {
        super(identifiable, CartsEndpoint.ENDPOINT);
    }
}
