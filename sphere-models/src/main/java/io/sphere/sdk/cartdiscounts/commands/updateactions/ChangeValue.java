package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountValue;
import io.sphere.sdk.commands.UpdateAction;

public class ChangeValue extends UpdateAction<CartDiscount> {
    private final CartDiscountValue value;

    private ChangeValue(final CartDiscountValue value) {
        super("changeValue");
        this.value = value;
    }

    public CartDiscountValue getValue() {
        return value;
    }

    public static ChangeValue of(final CartDiscountValue value) {
        return new ChangeValue(value);
    }
}
