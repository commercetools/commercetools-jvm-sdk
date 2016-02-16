package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.discountcodes.DiscountCode;

/**
 * Adds a discount code to the cart.
 *
 *  {@doc.gen intro}
 *
 *  {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#removeDiscountCode()}
 *
 *  @see Cart#getDiscountCodes()
 *  @see CartDiscount#isRequiringDiscountCode()
 */
public final class AddDiscountCode extends UpdateActionImpl<Cart> {
    private final String code;

    private AddDiscountCode(final String code) {
        super("addDiscountCode");
        this.code = code;
    }

    public static AddDiscountCode of(final DiscountCode code) {
        return of(code.getCode());
    }

    public static AddDiscountCode of(final String code) {
        return new AddDiscountCode(code);
    }

    public String getCode() {
        return code;
    }
}
