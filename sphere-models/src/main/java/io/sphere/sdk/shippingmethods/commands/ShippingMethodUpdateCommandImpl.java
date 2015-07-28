package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.List;

import static java.util.Arrays.asList;


final class ShippingMethodUpdateCommandImpl extends UpdateCommandDslImpl<ShippingMethod, ShippingMethodUpdateCommand> implements ShippingMethodUpdateCommand {
    ShippingMethodUpdateCommandImpl(final Versioned<ShippingMethod> versioned, final List<? extends UpdateAction<ShippingMethod>> updateActions) {
        super(versioned, updateActions, ShippingMethodEndpoint.ENDPOINT);
    }
}
