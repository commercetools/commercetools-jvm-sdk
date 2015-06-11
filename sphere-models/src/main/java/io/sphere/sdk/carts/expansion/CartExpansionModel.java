package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.queries.ExpansionModel;

import java.util.Optional;

public class CartExpansionModel<T> extends ExpansionModel<T> {
    CartExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    CartExpansionModel() {
        super();
    }

    public static CartExpansionModel<Cart> of() {
        return new CartExpansionModel<>();
    }
}
