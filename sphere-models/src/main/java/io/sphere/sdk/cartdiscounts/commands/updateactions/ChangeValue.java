package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountValue;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Changes the value of the discount.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#changeValue()}
 */
public final class ChangeValue extends UpdateActionImpl<CartDiscount> {
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
