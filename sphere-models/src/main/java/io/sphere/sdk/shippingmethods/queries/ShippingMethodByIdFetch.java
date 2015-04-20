package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.queries.ByIdFetchImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;

/**
 * Fetches a shipping method by ID.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdFetchTest#execution()}
 */
public class ShippingMethodByIdFetch extends ByIdFetchImpl<ShippingMethod> {
    private ShippingMethodByIdFetch(final String id) {
        super(id, ShippingMethodEndpoint.ENDPOINT);
    }

    public static ShippingMethodByIdFetch of(final String id) {
        return new ShippingMethodByIdFetch(id);
    }
}
