package io.sphere.sdk.productdiscounts.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.productdiscounts.ProductDiscount;

/**
 * Enables or disables the discount.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandIntegrationTest#changeIsActive()}
 */
public final class ChangeIsActive extends UpdateActionImpl<ProductDiscount> {
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
