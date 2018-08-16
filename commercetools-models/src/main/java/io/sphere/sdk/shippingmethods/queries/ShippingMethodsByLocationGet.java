package io.sphere.sdk.shippingmethods.queries;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.queries.MetaModelGetDsl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import javax.money.CurrencyUnit;
import java.util.List;

/**
 * Retrieves all the shipping methods that can ship to the given location.
 * If the currency parameter is given, then the shipping methods must also have a rate defined in the specified currency.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByLocationGetIntegrationTest#execution()}
 */
public interface ShippingMethodsByLocationGet extends MetaModelGetDsl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>> {
    static ShippingMethodsByLocationGet of(final CountryCode countryCode) {
        return of(countryCode, null, null);
    }

    static ShippingMethodsByLocationGet of(final CountryCode countryCode, final String state) {
        return of(countryCode, state, null);
    }

    static ShippingMethodsByLocationGet of(final CountryCode countryCode, final CurrencyUnit currencyUnit) {
        return of(countryCode, null, currencyUnit);
    }

    static ShippingMethodsByLocationGet of(final CountryCode countryCode, final String state, final CurrencyUnit currencyUnit) {
        return new ShippingMethodsByLocationGetImpl(countryCode, state, currencyUnit);
    }
}
