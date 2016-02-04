package io.sphere.sdk.cartdiscounts.expansion;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;
import java.util.List;

public interface CartDiscountExpansionModel<T> extends ExpansionPathContainer<T> {
    ExpansionPathContainer<T> references();

    static CartDiscountExpansionModel<CartDiscount> of() {
        return new CartDiscountExpansionModelImpl<>();
    }

    static <T> CartDiscountExpansionModel<T> of(@Nullable final List<String> parentPaths, @Nullable final String path) {
        return new CartDiscountExpansionModelImpl<>(parentPaths, path);
    }
}
