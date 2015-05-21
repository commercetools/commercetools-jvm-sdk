package io.sphere.sdk.cartdiscounts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartDiscountTarget;
import io.sphere.sdk.commands.UpdateAction;

/**
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#changeTarget()}
 */
public class ChangeTarget extends UpdateAction<CartDiscount> {
    private final CartDiscountTarget target;

    private ChangeTarget(final CartDiscountTarget target) {
        super("changeTarget");
        this.target = target;
    }

    public static ChangeTarget of(final CartDiscountTarget target) {
        return new ChangeTarget(target);
    }

    public CartDiscountTarget getTarget() {
        return target;
    }
}
