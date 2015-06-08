package io.sphere.sdk.shippingmethods.queries;

import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.queries.MetaModelFetchDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

/**
 * Fetches a shipping method by ID.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdFetchTest#execution()}
 */
public interface ShippingMethodByIdFetch extends MetaModelFetchDsl<ShippingMethod, ShippingMethodByIdFetch, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodByIdFetch of(final Identifiable<ShippingMethod> cartDiscount) {
        return of(cartDiscount.getId());
    }

    static ShippingMethodByIdFetch of(final String id) {
        return new ShippingMethodByIdFetchImpl(id);
    }
}

