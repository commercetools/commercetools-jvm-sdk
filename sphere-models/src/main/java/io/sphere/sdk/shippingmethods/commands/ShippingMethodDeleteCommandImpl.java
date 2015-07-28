package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.ByIdDeleteCommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;

public class ShippingMethodDeleteCommandImpl extends ByIdDeleteCommandImpl<ShippingMethod> {
    private ShippingMethodDeleteCommandImpl(final Versioned<ShippingMethod> versioned) {
        super(versioned, ShippingMethodEndpoint.ENDPOINT);
    }

    public static DeleteCommand<ShippingMethod> of(final Versioned<ShippingMethod> versioned) {
        return new ShippingMethodDeleteCommandImpl(versioned);
    }
}
