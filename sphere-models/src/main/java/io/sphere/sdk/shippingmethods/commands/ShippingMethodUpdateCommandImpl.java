package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.*;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;


final class ShippingMethodUpdateCommandImpl extends MetaModelUpdateCommandDslImpl<ShippingMethod, ShippingMethodUpdateCommand, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodUpdateCommand {
    ShippingMethodUpdateCommandImpl(final Versioned<ShippingMethod> versioned, final List<? extends UpdateAction<ShippingMethod>> updateActions) {
        super(versioned, updateActions, ShippingMethodEndpoint.ENDPOINT, ShippingMethodUpdateCommandImpl::new, ShippingMethodExpansionModel.of());
    }

    ShippingMethodUpdateCommandImpl(final MetaModelUpdateCommandDslBuilder<ShippingMethod, ShippingMethodUpdateCommand, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }
}
