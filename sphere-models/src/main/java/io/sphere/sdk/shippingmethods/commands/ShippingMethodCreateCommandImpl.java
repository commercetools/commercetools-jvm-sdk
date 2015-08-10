package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandBuilder;
import io.sphere.sdk.commands.ReferenceExpandeableCreateCommandImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

/**
 * Creates a {@link ShippingMethod} in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandTest#execution()}
 */
final class ShippingMethodCreateCommandImpl extends ReferenceExpandeableCreateCommandImpl<ShippingMethod, ShippingMethodCreateCommand, ShippingMethodDraft, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodCreateCommand {
    ShippingMethodCreateCommandImpl(final ReferenceExpandeableCreateCommandBuilder<ShippingMethod, ShippingMethodCreateCommand, ShippingMethodDraft, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }

    ShippingMethodCreateCommandImpl(final ShippingMethodDraft draft) {
        super(draft, ShippingMethodEndpoint.ENDPOINT, ShippingMethodExpansionModel.of(), ShippingMethodCreateCommandImpl::new);
    }
}
