package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.FetchByIdImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;

/**
 * Fetches a shipping method by ID.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodFetchByIdTest#execution()}
 */
public class ShippingMethodFetchById extends FetchByIdImpl<ShippingMethod> {
    private ShippingMethodFetchById(final String id) {
        super(id, ShippingMethodEndpoint.ENDPOINT);
    }

    public static ShippingMethodFetchById of(final String id) {
        return new ShippingMethodFetchById(id);
    }
}
