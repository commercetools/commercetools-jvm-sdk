package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;

public interface ShippingMethodDeleteCommand extends ByIdDeleteCommand<ShippingMethod> {
    static DeleteCommand<ShippingMethod> of(final Versioned<ShippingMethod> versioned) {
        return new ShippingMethodDeleteCommandImpl(versioned);
    }
}
