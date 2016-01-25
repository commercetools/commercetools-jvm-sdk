package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.CreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

/**
 * Creates a {@link io.sphere.sdk.shippingmethods.ShippingMethod} in SPHERE.IO.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandTest#execution()}
 */
public interface ShippingMethodCreateCommand extends CreateCommand<ShippingMethod>, MetaModelReferenceExpansionDsl<ShippingMethod, ShippingMethodCreateCommand, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodCreateCommand of(final ShippingMethodDraft draft) {
        return new ShippingMethodCreateCommandImpl(draft);
    }
}
