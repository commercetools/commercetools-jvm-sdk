package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.expansion.CartDiscountExpansionModel;
import io.sphere.sdk.cartdiscounts.queries.CartDiscountByIdGet;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;

/**
 * Retrieves all the shipping methods that can ship to the shipping address of the given cart.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByCartGetIntegrationTest#execution()}
 */
public interface ShippingMethodsByCartGet extends MetaModelGetDsl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodsByCartGet of(final Referenceable<Cart> cart) {
        return of(cart.toReference().getId());
    }

    static ShippingMethodsByCartGet of(final String cartId) {
        return new ShippingMethodsByCartGetImpl(cartId);
    }

}
