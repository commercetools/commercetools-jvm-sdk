package io.sphere.sdk.carts;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.models.Base;

public final class CartEndpoint extends Base{

    public static final JsonEndpoint<Cart> ENDPOINT = JsonEndpoint.of(Cart.typeReference(), "/carts");

    private CartEndpoint(){}

}
