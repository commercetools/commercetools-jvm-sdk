package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.Identifiable;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.expansion.ShippingMethodExpansionModel;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByOrderEditGetIntegrationTest#execution()}
 */
final class ShippingMethodsByOrderEditGetImpl extends MetaModelGetDslImpl<List<ShippingMethod>, ShippingMethod, ShippingMethodsByCartGet, ShippingMethodExpansionModel<ShippingMethod>>
        implements ShippingMethodsByOrderEditGet {
    ShippingMethodsByOrderEditGetImpl(final Identifiable<OrderEdit> orderEdit, final CountryCode country, @Nullable final String state) {
        super(ShippingMethodEndpoint.ENDPOINT.withTypeReference(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }), "", ShippingMethodExpansionModel.of(), ShippingMethodsByCartGetImpl::new, createQueryParameters(orderEdit, country, state));
    }

    private static List<NameValuePair> createQueryParameters(final Identifiable<OrderEdit> orderEdit, final CountryCode country, @Nullable final String state) {
        final List<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(NameValuePair.of("orderEdit", orderEdit.getId()));
        queryParameters.add(NameValuePair.of("country", country.getAlpha2()));
        Optional.ofNullable(state).ifPresent(x -> queryParameters.add(NameValuePair.of("state", x)));

        return queryParameters;
    }
}
