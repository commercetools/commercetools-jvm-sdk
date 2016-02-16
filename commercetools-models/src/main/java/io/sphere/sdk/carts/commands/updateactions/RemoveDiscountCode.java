package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

/**
 * Removes a discount code.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#removeDiscountCode()}
 */
public final class RemoveDiscountCode extends UpdateActionImpl<Cart> {
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
