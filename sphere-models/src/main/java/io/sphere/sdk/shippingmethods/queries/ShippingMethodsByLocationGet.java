package io.sphere.sdk.shippingmethods.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.http.UrlQueryBuilder;

import javax.money.CurrencyUnit;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.http.HttpMethod.GET;

/**
 * Retrieves all the shipping methods that can ship to the given location.
 * If the currency parameter is given, then the shipping methods must also have a rate defined in the specified currency.
 *
 * {@include.example io.sphere.sdk.shippingmethods.queries.ShippingMethodsByLocationGetTest#execution()}
 */
public class ShippingMethodsByLocationGet extends SphereRequestBase implements SphereRequest<List<ShippingMethod>> {
    private final CountryCode country;
    private final Optional<String> state;
    private final Optional<CurrencyUnit> currency;

    private ShippingMethodsByLocationGet(final CountryCode country, final Optional<String> state, final Optional<CurrencyUnit> currency) {
        this.country = country;
        this.state = state;
        this.currency = currency;
    }

    @Override
    public List<ShippingMethod> deserialize(final HttpResponse httpResponse) {
        return resultMapperOf(new TypeReference<List<ShippingMethod>>() {
            @Override
            public String toString() {
                return "TypeReference<List<ShippingMethod>>";
            }
        }).apply(httpResponse);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(GET, "/shipping-methods" + queryParameters());
    }

    public static ShippingMethodsByLocationGet of(final CountryCode countryCode) {
        return new ShippingMethodsByLocationGet(countryCode, Optional.<String>empty(), Optional.<CurrencyUnit>empty());
    }

    public ShippingMethodsByLocationGet withState(final String state) {
        return new ShippingMethodsByLocationGet(country, Optional.of(state), currency);
    }

    public ShippingMethodsByLocationGet withCurrency(final CurrencyUnit currency) {
        return new ShippingMethodsByLocationGet(country, state, Optional.of(currency));
    }

    private String queryParameters() {
        final UrlQueryBuilder urlQueryBuilder = UrlQueryBuilder.of();
        urlQueryBuilder.add("country", country.getAlpha2());
        state.ifPresent(x -> urlQueryBuilder.add("state", x));
        currency.ifPresent(x -> urlQueryBuilder.add("currency", x.getCurrencyCode()));

        return urlQueryBuilder.toStringWithOptionalQuestionMark();
    }
}
