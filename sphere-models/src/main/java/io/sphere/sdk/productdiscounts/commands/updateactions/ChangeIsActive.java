package io.sphere.sdk.productdiscounts.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.productdiscounts.ProductDiscount;

/**
 * {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandTest#changeIsActive()}
 */
public class ChangeIsActive extends UpdateAction<ProductDiscount> {
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
