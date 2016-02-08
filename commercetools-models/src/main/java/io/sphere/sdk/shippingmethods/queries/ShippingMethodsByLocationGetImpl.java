package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Retrieves all the shipping methods that can ship to the given location.
 * If the currency parameter is given, then the shipping methods must also have a rate defined in the specified currency.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByLocationGetTest#execution()}
 */
final class ShippingMethodsByLocationGetImpl extends MetaModelGetDslImpl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByLocationGetImpl, ShippingMethodExpansionModel<ShippingMethod>> implements ShippingMethodsByLocationGet {
    ShippingMethodsByLocationGetImpl(final CountryCode country, @Nullable final String state, @Nullable final CurrencyUnit currency) {
        super(identifierToSearchFor(requireNonNull(country), state, currency), ShippingMethodEndpoint.ENDPOINT.withTypeReference(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }), ShippingMethodExpansionModel.of(), ShippingMethodsByLocationGetImpl::new);
    }

    public ShippingMethodsByLocationGetImpl(final MetaModelGetDslBuilder<List<ShippingMethod>, ShippingMethod, ShippingMethodsByLocationGetImpl, ShippingMethodExpansionModel<ShippingMethod>> builder) {
        super(builder);
    }

    private static String identifierToSearchFor(final CountryCode country, @Nullable final String state, @Nullable final CurrencyUnit currency) {
        final UrlQueryBuilder urlQueryBuilder = UrlQueryBuilder.of();
        urlQueryBuilder.add("country", country.getAlpha2());
        Optional.ofNullable(state).ifPresent(x -> urlQueryBuilder.add("state", x));
        Optional.ofNullable(currency).ifPresent(x -> urlQueryBuilder.add("currency", x.getCurrencyCode()));

        return urlQueryBuilder.toStringWithOptionalQuestionMark();
    }
}
