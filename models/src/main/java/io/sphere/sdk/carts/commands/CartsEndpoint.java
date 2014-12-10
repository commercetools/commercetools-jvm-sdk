package io.sphere.sdk.carts.commands;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.http.JsonEndpoint;

final class CartsEndpoint {
    static final JsonEndpoint<Cart> ENDPOINT = JsonEndpoint.of(Cart.typeReference(), "/carts");
}
