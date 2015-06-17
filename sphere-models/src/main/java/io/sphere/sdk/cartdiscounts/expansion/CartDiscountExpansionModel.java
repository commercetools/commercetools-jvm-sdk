package io.sphere.sdk.cartdiscounts.expansion;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.expansion.ExpansionModel;

public class CartDiscountExpansionModel<T>  extends ExpansionModel<T> {
    public static CartDiscountExpansionModel<CartDiscount> of() {
        return new CartDiscountExpansionModel<>();
    }
}
