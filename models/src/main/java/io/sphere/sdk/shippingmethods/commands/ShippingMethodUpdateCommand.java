package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class ShippingMethodUpdateCommand extends UpdateCommandDslImpl<ShippingMethod> {
    private ShippingMethodUpdateCommand(final Versioned<ShippingMethod> versioned, final List<? extends UpdateAction<ShippingMethod>> updateActions) {
        super(versioned, updateActions, ShippingMethodEndpoint.ENDPOINT);
    }

    public static ShippingMethodUpdateCommand of(final Versioned<ShippingMethod> versioned,
                                                 final List<? extends UpdateAction<ShippingMethod>> updateActions) {
        return new ShippingMethodUpdateCommand(versioned, updateActions);
    }

    public static ShippingMethodUpdateCommand of(final Versioned<ShippingMethod> versioned,
                                                 final UpdateAction<ShippingMethod> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
