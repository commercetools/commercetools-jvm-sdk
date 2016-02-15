package io.sphere.sdk.shippingmethods.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import javax.annotation.Nullable;

/**
 * Sets the description.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandIntegrationTest#setDescription()}
 */
public final class SetDescription extends UpdateActionImpl<ShippingMethod> {
    @Nullable
    private final String description;

    private SetDescription(@Nullable final String description) {
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
