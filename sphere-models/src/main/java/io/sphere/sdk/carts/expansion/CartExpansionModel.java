package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.carts.Cart;

import java.util.Optional;

public class CartExpansionModel<T> extends CartLikeExpansionModel<T> {
    CartExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, path);
    }

    CartExpansionModel() {
        super();
    }

    public static CartExpansionModel<Cart> of() {
        return new CartExpansionModel<>();
    }
}
