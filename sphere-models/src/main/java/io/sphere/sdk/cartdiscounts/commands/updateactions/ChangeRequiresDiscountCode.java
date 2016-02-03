package io.sphere.sdk.cartdiscounts.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Changes the property if the discount requires a discount code.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandTest#changeRequiresDiscountCode()}
 */
public final class ChangeRequiresDiscountCode extends UpdateActionImpl<CartDiscount> {
    private final boolean requiresDiscountCode;

    private ChangeRequiresDiscountCode(final boolean requiresDiscountCode) {
        super("changeRequiresDiscountCode");
        this.requiresDiscountCode = requiresDiscountCode;
    }

    public static ChangeRequiresDiscountCode of(final boolean requiresDiscountCode) {
        return new ChangeRequiresDiscountCode(requiresDiscountCode);
    }

    @JsonProperty("requiresDiscountCode")
    public boolean isRequiringDiscountCode() {
        return requiresDiscountCode;
    }
}
