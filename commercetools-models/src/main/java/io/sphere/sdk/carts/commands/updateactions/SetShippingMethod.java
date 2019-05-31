package io.sphere.sdk.carts.commands.updateactions;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import javax.annotation.Nullable;

/**
 * Sets the shipping method.
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setShippingMethod()}
 * {@include.example io.sphere.sdk.carts.commands.CartUpdateCommandIntegrationTest#setShippingMethodById()}
 */
public final class SetShippingMethod extends UpdateActionImpl<Cart> {
    @Nullable
    private final ResourceIdentifier<ShippingMethod> shippingMethod;

    private SetShippingMethod(@Nullable final ResourceIdentifier<ShippingMethod> shippingMethod) {
        super("setShippingMethod");
        this.shippingMethod = shippingMethod;
    }

    @Nullable
    public ResourceIdentifier<ShippingMethod> getShippingMethod() {
        return shippingMethod;
    }

    /**
     * This method is deprecated, please use {@link SetShippingMethod#of(ResourceIdentifier)}
     */
    @Deprecated
    public static SetShippingMethod ofReferencable(@Nullable final Referenceable<ShippingMethod> shippingMethod) {
        return shippingMethod != null
                ? new SetShippingMethod(shippingMethod.toReference())
                : ofRemove();
    }

    public static SetShippingMethod of(@Nullable final ResourceIdentifier<ShippingMethod> shippingMethod) {
        return  new SetShippingMethod(shippingMethod);
    }

    public static SetShippingMethod ofId(@Nullable String shippingMethodId) {
        return shippingMethodId != null
                ? of(ShippingMethod.referenceOfId(shippingMethodId).toResourceIdentifier())
                : ofRemove();
    }

    public static SetShippingMethod ofRemove() {
        return new SetShippingMethod(null);
    }
}
