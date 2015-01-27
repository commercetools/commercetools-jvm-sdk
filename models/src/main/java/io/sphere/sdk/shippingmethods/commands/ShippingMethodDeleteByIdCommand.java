package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;

public class ShippingMethodDeleteByIdCommand extends DeleteByIdCommandImpl<ShippingMethod> {
    private ShippingMethodDeleteByIdCommand(final Versioned<ShippingMethod> versioned) {
        super(versioned, ShippingMethodEndpoint.ENDPOINT);
    }

    public static ShippingMethodDeleteByIdCommand of(final Versioned<ShippingMethod> versioned) {
        return new ShippingMethodDeleteByIdCommand(versioned);
    }
}
