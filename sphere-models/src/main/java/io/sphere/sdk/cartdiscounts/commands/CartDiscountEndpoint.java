package io.sphere.sdk.cartdiscounts.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.client.JsonEndpoint;

final class CartDiscountEndpoint {
    public static final JsonEndpoint<CartDiscount> ENDPOINT = JsonEndpoint.of(CartDiscount.typeReference(), "/cart-discounts");
}
