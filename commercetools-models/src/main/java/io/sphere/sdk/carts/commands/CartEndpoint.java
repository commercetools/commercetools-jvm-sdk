package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.JsonEndpoint;

final class CartEndpoint {
    static final JsonEndpoint<Cart> ENDPOINT = JsonEndpoint.of(Cart.typeReference(), "/carts");
}
