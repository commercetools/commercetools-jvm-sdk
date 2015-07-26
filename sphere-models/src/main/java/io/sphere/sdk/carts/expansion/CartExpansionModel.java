package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.carts.Cart;

public class CartExpansionModel<T> extends CartLikeExpansionModel<T> {
    private CartExpansionModel() {
        super();
    }

    public static CartExpansionModel<Cart> of() {
        return new CartExpansionModel<>();
    }
}
