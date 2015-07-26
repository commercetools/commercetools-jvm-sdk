package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import javax.annotation.Nullable;

/**
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandTest#setDescription()}
 */
public class SetDescription extends UpdateAction<ShippingMethod> {
    @Nullable
    private final String description;

    private SetDescription(final String description) {
        super("setDescription");
        this.description = description;
    }

    public static SetDescription of(@Nullable final String description) {
        return new SetDescription(description);
    }

    @Nullable
    public String getDescription() {
        return description;
    }
}
