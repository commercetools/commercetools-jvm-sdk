package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;

final class ShippingMethodDeleteCommandImpl extends ByIdDeleteCommandImpl<ShippingMethod> {
    ShippingMethodDeleteCommandImpl(final Versioned<ShippingMethod> versioned) {
        super(versioned, ShippingMethodEndpoint.ENDPOINT);
    }
}
