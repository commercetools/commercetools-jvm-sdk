package io.sphere.sdk.products.search;

import io.sphere.sdk.http.NameValuePair;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public final class PriceSelectionQueryParameters {
    public static final String PRICE_CURRENCY = "priceCurrency";
    public static final String PRICE_COUNTRY = "priceCountry";
    public static final String PRICE_CUSTOMER_GROUP = "priceCustomerGroup";
    public static final String PRICE_CHANNEL = "priceChannel";
    public static final List<String> ALL_PARAMETERS = asList(PRICE_CURRENCY, PRICE_COUNTRY, PRICE_CUSTOMER_GROUP, PRICE_CHANNEL);

    private PriceSelectionQueryParameters() {
    }

    /**
     * SDK internal method to to add price selection query parameters to the additional query parameters list.
     * If {@code priceSelection} is null, the price selection parameters will be removed.
     *
     * @param priceSelection the new price selection which should be applied or null to remove the price selection
     * @param currentParameters list containing the additional query parameters, won't be changed
     * @return a new list with additional query parameters where the priceSelection is applied
     */
    public static List<NameValuePair> getQueryParametersWithPriceSelection(@Nullable final PriceSelection priceSelection, final List<NameValuePair> currentParameters) {
        final List<NameValuePair> currentParametersWithoutPriceSelectionParameters = currentParameters.stream()
                .filter(pair -> !ALL_PARAMETERS.contains(pair.getName()))
                .collect(toList());
        final List<NameValuePair> resultingParameters = new LinkedList<>(currentParametersWithoutPriceSelectionParameters);
        if (priceSelection != null && priceSelection.getPriceCurrency() != null) {
            addParamIfNotNull(resultingParameters, PRICE_CURRENCY, priceSelection.getPriceCurrency());
            addParamIfNotNull(resultingParameters, PRICE_COUNTRY, priceSelection.getPriceCountry());
            addParamIfNotNull(resultingParameters, PRICE_CUSTOMER_GROUP, priceSelection.getPriceCustomerGroup());
            addParamIfNotNull(resultingParameters, PRICE_CHANNEL, priceSelection.getPriceChannel());
        }
        return resultingParameters;
    }

    private static void addParamIfNotNull(final List<NameValuePair> resultingParameters, final String name, final String value) {
        if (value != null) {
            resultingParameters.add(NameValuePair.of(name, value));
        }
    }
}
