package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

/**
 * Creates a {@link io.sphere.sdk.shippingmethods.ShippingMethod}.
 *
 * {@include.example io.sphere.sdk.shippingmethods.commands.ShippingMethodCreateCommandIntegrationTest#execution()}
 */
public interface ShippingMethodCreateCommand extends DraftBasedCreateCommand<ShippingMethod, ShippingMethodDraft>, MetaModelReferenceExpansionDsl<ShippingMethod, ShippingMethodCreateCommand, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodCreateCommand of(final ShippingMethodDraft draft) {
        return new ShippingMethodCreateCommandImpl(draft);
    }
}
