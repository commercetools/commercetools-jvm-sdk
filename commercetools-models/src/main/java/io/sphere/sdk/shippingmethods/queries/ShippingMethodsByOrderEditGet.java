package io.sphere.sdk.shippingmethods.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import java.util.List;

/**
 * Retrieves all the shipping methods that can ship to the given orderEdit.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByOrderEditGetIntegrationTest#execution()}
 */
public interface ShippingMethodsByOrderEditGet extends MetaModelGetDsl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodsByOrderEditGet of(final Identifiable<OrderEdit> orderEdit, final CountryCode countryCode) {
        return of(orderEdit, countryCode, null);
    }

    static ShippingMethodsByOrderEditGet of(final Identifiable<OrderEdit> orderEdit, final CountryCode countryCode, final String state) {
        return new ShippingMethodsByOrderEditGetImpl(orderEdit, countryCode, state);
    }
}
