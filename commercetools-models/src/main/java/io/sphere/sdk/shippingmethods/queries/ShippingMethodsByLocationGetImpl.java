package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Retrieves all the shipping methods that can ship to the given location.
 * If the currency parameter is given, then the shipping methods must also have a rate defined in the specified currency.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByLocationGetIntegrationTest#execution()}
 */
final class ShippingMethodsByLocationGetImpl extends MetaModelGetDslImpl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>>
        implements ShippingMethodsByLocationGet {
    ShippingMethodsByLocationGetImpl(final CountryCode country, @Nullable final String state, @Nullable final CurrencyUnit currency) {
        super(ShippingMethodEndpoint.ENDPOINT.withTypeReference(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }), "", ShippingMethodExpansionModel.of(), ShippingMethodsByCartGetImpl::new, createQueryParameters(country, state, currency));
    }

    private static List<NameValuePair> createQueryParameters(final CountryCode country, @Nullable final String state, @Nullable final CurrencyUnit currency) {
        final List<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(NameValuePair.of("country", country.getAlpha2()));
        Optional.ofNullable(state).ifPresent(x -> queryParameters.add(NameValuePair.of("state", x)));
        Optional.ofNullable(currency).ifPresent(x -> queryParameters.add(NameValuePair.of("currency", x.getCurrencyCode())));

        return queryParameters;
    }
}
