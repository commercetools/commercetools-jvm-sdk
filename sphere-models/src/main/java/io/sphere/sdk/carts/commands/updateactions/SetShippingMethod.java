package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;

/**
 * Sets the shipping method.
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandTest#setShippingMethod()}
 */
public class SetShippingMethod extends UpdateAction<Cart> {
    private final Reference<ShippingMethod> shippingMethod;

    private SetShippingMethod(final Reference<ShippingMethod> shippingMethod) {
        super("setShippingMethod");
        this.shippingMethod = shippingMethod;
    }

    public Reference<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }

    public static SetShippingMethod of(final Referenceable<ShippingMethod> shippingMethod) {
        return new SetShippingMethod(shippingMethod.toReference());
    }
}
