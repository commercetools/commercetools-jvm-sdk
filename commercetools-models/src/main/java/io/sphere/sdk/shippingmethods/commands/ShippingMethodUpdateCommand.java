package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface ShippingMethodUpdateCommand extends UpdateCommandDsl<ShippingMethod, ShippingMethodUpdateCommand>, MetaModelReferenceExpansionDsl<ShippingMethod, ShippingMethodUpdateCommand, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodUpdateCommand of(final Versioned<ShippingMethod> versioned,
                                                 final List<? extends UpdateAction<ShippingMethod>> updateActions) {
        return new ShippingMethodUpdateCommandImpl(versioned, updateActions);
    }

    static ShippingMethodUpdateCommand of(final Versioned<ShippingMethod> versioned, final UpdateAction<ShippingMethod> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }
}
