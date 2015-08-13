package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.ByIdDeleteCommand;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

public interface ShippingMethodDeleteCommand extends ByIdDeleteCommand<ShippingMethod>, MetaModelExpansionDsl<ShippingMethod, ShippingMethodDeleteCommand, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodDeleteCommand of(final Versioned<ShippingMethod> versioned) {
        return new ShippingMethodDeleteCommandImpl(versioned);
    }
}
