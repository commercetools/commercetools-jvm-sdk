package io.sphere.sdk.products.search;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public final class StoreSelectionQueryParameters extends Base {
    public static final String LOCALE_PROJECTION = "localeProjection";
    public static final String STORE_PROJECTION = "storeProjection";
    public static final List<String> ALL_PARAMETERS = asList(LOCALE_PROJECTION, STORE_PROJECTION);

    private StoreSelectionQueryParameters() {
    }

    /**
     * SDK internal method to to add price selection query parameters to the additional query parameters list.
     * If {@code localeSelection} is null, the price selection parameters will be removed.
     *
     * @param localeSelection the new price selection which should be applied or null to remove the price selection
     * @param currentParameters list containing the additional query parameters, won't be changed
     * @return a new list with additional query parameters where the localeSelection is applied
     */
    public static List<NameValuePair> getQueryParametersWithStoreSelection(@Nullable final StoreSelection localeSelection, final List<NameValuePair> currentParameters) {
        final List<NameValuePair> currentParametersWithoutStoreSelectionParameters = currentParameters.stream()
                .filter(pair -> !ALL_PARAMETERS.contains(pair.getName()))
                .collect(toList());
        final List<NameValuePair> resultingParameters = new LinkedList<>(currentParametersWithoutStoreSelectionParameters);

        if (localeSelection != null && localeSelection.getStoreProjection() != null) {
            addParamIfNotNull(resultingParameters, STORE_PROJECTION, localeSelection.getStoreProjection().toString());
        }
        return resultingParameters;
    }

    @Nullable
    public static StoreSelection extractStoreSelectionFromHttpQueryParameters(final List<NameValuePair> additionalHttpQueryParameters) {
        final List<NameValuePair> localeSelectionCandidates = additionalHttpQueryParameters
                .stream()
                .filter(pair -> ALL_PARAMETERS.contains(pair.getName()))
                .collect(Collectors.toList());
        final boolean containsStoreSelection = localeSelectionCandidates.stream()
                .anyMatch(pair -> STORE_PROJECTION.equals(pair.getName()));
        return containsStoreSelection ? extractStoreSelection(localeSelectionCandidates) : null;
    }

    private static StoreSelection extractStoreSelection(final List<NameValuePair> localeSelectionCandidates) {
        final Map<String, String> map = NameValuePair.convertToStringMap(localeSelectionCandidates);
        return StoreSelectionBuilder.of(map.get(STORE_PROJECTION))
                .build();
    }

    private static void addParamIfNotNull(final List<NameValuePair> resultingParameters, final String name, final String value) {
        if (value != null) {
            resultingParameters.add(NameValuePair.of(name, value));
        }
    }
}
