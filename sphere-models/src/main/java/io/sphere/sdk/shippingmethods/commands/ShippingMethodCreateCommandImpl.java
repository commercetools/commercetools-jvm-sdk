package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;

/**
 * Creates a {@link ShippingMethod} in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandTest#execution()}
 */
public class ShippingMethodCreateCommandImpl extends CreateCommandImpl<ShippingMethod, ShippingMethodDraft> {
    private ShippingMethodCreateCommandImpl(final ShippingMethodDraft draft) {
        super(draft, ShippingMethodEndpoint.ENDPOINT);
    }

    public static ShippingMethodCreateCommandImpl of(final ShippingMethodDraft draft) {
        return new ShippingMethodCreateCommandImpl(draft);
    }
}
