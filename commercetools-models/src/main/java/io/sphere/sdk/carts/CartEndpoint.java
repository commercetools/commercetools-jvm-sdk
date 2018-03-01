package io.sphere.sdk.carts;

import io.sphere.sdk.client.JsonEndpoint;

public final class CartEndpoint {

    public static final JsonEndpoint<Cart> ENDPOINT = JsonEndpoint.of(Cart.typeReference(), "/carts");

    private CartEndpoint(){}

}
