package io.sphere.sdk.cartdiscounts.expansion;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.expansion.ExpandedModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import javax.annotation.Nullable;
import java.util.List;

public final class CartDiscountExpansionModel<T> extends ExpandedModel<T> {

    private CartDiscountExpansionModel() {
    }

    private CartDiscountExpansionModel(@Nullable final List<String> parentPaths, @Nullable final String path) {
        super(parentPaths, path);
    }

    public ExpansionPathContainer<T> references() {
        return expansionPath("references[*]");
    }

    public static CartDiscountExpansionModel<CartDiscount> of() {
        return new CartDiscountExpansionModel<>();
    }

    public static <T> CartDiscountExpansionModel<T> of(@Nullable final List<String> parentPaths, @Nullable final String path) {
        return new CartDiscountExpansionModel<>(parentPaths, path);
    }
}
