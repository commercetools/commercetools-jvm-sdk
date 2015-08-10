package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.MetaModelByIdDeleteCommandBuilder;
import io.sphere.sdk.commands.MetaModelByIdDeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

final class ShippingMethodDeleteCommandImpl extends MetaModelByIdDeleteCommandImpl<ShippingMethod, ShippingMethodDeleteCommand, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodDeleteCommand {
    ShippingMethodDeleteCommandImpl(final Versioned<ShippingMethod> versioned) {
        super(versioned, ShippingMethodEndpoint.ENDPOINT, ShippingMethodExpansionModel.of(), ShippingMethodDeleteCommandImpl::new);
    }


    ShippingMethodDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<ShippingMethod, ShippingMethodDeleteCommand, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}
