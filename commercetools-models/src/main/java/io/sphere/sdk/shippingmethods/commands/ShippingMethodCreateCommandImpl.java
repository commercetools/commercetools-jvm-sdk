package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.MetaModelCreateCommandBuilder;
import io.sphere.sdk.commands.MetaModelCreateCommandImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

/**
 * Creates a {@link ShippingMethod}.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandIntegrationTest#execution()}
 */
final class ShippingMethodCreateCommandImpl extends MetaModelCreateCommandImpl<ShippingMethod, ShippingMethodCreateCommand, ShippingMethodDraft, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodCreateCommand {
    ShippingMethodCreateCommandImpl(final MetaModelCreateCommandBuilder<ShippingMethod, ShippingMethodCreateCommand, ShippingMethodDraft, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }

    ShippingMethodCreateCommandImpl(final ShippingMethodDraft draft) {
        super(draft, ShippingMethodEndpoint.ENDPOINT, ShippingMethodExpansionModel.of(), ShippingMethodCreateCommandImpl::new);
    }
}
