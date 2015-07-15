package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.shippingmethods.ShippingMethod;

import java.util.List;

/**
 * Retrieves all the shipping methods that can ship to the shipping address of the given cart.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByCartGetTest#execution()}
 */
public interface ShippingMethodsByCartGet extends SphereRequest<List<ShippingMethod>> {
    static ShippingMethodsByCartGet of(final Referenceable<Cart> cart) {
        return of(cart.toReference().getId());
    }

    static ShippingMethodsByCartGet of(final String cartId) {
        return new ShippingMethodsByCartGetImpl(cartId);
    }
}
