package io.sphere.sdk.shippingmethods.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;

/**
 * Sets a shipping method as default or not.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodUpdateCommandIntegrationTest#changeIsDefault()}
 */
public final class ChangeIsDefault extends UpdateActionImpl<ShippingMethod> {
    private final boolean isDefault;

    private ChangeIsDefault(final boolean isDefault) {
        super("changeIsDefault");
        this.isDefault = isDefault;
    }

    @JsonProperty("isDefault")
    public boolean isDefault() {
        return isDefault;
    }

    public static ChangeIsDefault of(final boolean isDefault) {
        return new ChangeIsDefault(isDefault);
    }

    public static ChangeIsDefault toTrue() {
        return of(true);
    }

    public static ChangeIsDefault toFalse() {
        return of(false);
    }
}
