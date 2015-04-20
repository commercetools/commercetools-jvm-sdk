package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;

/**
 * Creates a {@link io.sphere.sdk.shippingmethods.ShippingMethod} in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandTest#execution()}
 */
public class ShippingMethodCreateCommand extends CreateCommandImpl<ShippingMethod, ShippingMethodDraft> {
    private ShippingMethodCreateCommand(final ShippingMethodDraft draft) {
        super(draft, ShippingMethodEndpoint.ENDPOINT);
    }

    public static ShippingMethodCreateCommand of(final ShippingMethodDraft draft) {
        return new ShippingMethodCreateCommand(draft);
    }
}
