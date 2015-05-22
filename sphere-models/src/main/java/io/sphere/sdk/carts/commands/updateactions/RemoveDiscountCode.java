package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#removeDiscountCode()}
 */
public class RemoveDiscountCode extends UpdateAction<Cart> {
    private final Reference<DiscountCode> discountCode;

    private RemoveDiscountCode(final Reference<DiscountCode> discountCode) {
        super("removeDiscountCode");
        this.discountCode = discountCode;
    }

    public static RemoveDiscountCode of(final Referenceable<DiscountCode> discountCode) {
        return new RemoveDiscountCode(discountCode.toReference());
    }

    public Reference<DiscountCode> getDiscountCode() {
        return discountCode;
    }
}
