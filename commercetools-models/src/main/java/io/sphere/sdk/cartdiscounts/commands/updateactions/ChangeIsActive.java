package io.sphere.sdk.cartdiscounts.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.commands.UpdateActionImpl;

/**
 * Switches the state if the discount is active.
 *
 *  {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommandIntegrationTest#changeIsActive()}
 */
public final class ChangeIsActive extends UpdateActionImpl<CartDiscount> {
    private final boolean isActive;

    private ChangeIsActive(final boolean isActive) {
        super("changeIsActive");
        this.isActive = isActive;
    }

    public static ChangeIsActive of(final boolean isActive) {
        return new ChangeIsActive(isActive);
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }
}
