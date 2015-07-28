package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.CreateCommandImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;

/**
 * Creates a {@link ShippingMethod} in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandTest#execution()}
 */
final class ShippingMethodCreateCommandImpl extends CreateCommandImpl<ShippingMethod, ShippingMethodDraft> implements ShippingMethodCreateCommand {
    ShippingMethodCreateCommandImpl(final ShippingMethodDraft draft) {
        super(draft, ShippingMethodEndpoint.ENDPOINT);
    }
}
