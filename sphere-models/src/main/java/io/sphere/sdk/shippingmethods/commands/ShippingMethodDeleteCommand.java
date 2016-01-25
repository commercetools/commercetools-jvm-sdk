package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

public interface ShippingMethodDeleteCommand extends MetaModelReferenceExpansionDsl<ShippingMethod, ShippingMethodDeleteCommand, ShippingMethodExpansionModel<ShippingMethod>>, DeleteCommand<ShippingMethod> {
    static ShippingMethodDeleteCommand of(final Versioned<ShippingMethod> versioned) {
        return new ShippingMethodDeleteCommandImpl(versioned);
    }
}
