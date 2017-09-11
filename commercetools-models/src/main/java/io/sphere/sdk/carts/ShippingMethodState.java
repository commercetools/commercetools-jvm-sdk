package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

public enum ShippingMethodState implements SphereEnumeration {
    /**
     * The shippingMethod predicate does not match the cart.
     * Ordering this cart will fail with [ShippingMethodDoesNotMatchCart](/http-api-errors.html#orders-400-shipping-method-does-not-match-cart).
     */
    DOES_NOT_MATCH_CART,

    /**
     * Either the shippingMethod defines no predicate, or the predicate matches the cart.
     */
    MATCHES_CART;

    @JsonCreator
    public static ShippingMethodState ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
