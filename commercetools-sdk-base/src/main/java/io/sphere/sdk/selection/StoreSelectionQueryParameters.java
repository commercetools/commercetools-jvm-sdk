package io.sphere.sdk.selection;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public final class StoreSelectionQueryParameters extends Base {
    public static final String STORE_PROJECTION = "storeProjection";

    private StoreSelectionQueryParameters() {
    }

    public static List<NameValuePair> getQueryParametersWithStoreSelection(@Nullable final StoreSelection storeSelection, final List<NameValuePair> currentParameters) {
        final List<NameValuePair> currentParametersWithoutStoreSelectionParameters = currentParameters.stream()
                .filter(pair -> !STORE_PROJECTION.equals(pair.getName()))
                .collect(toList());
        final List<NameValuePair> resultingParameters = new LinkedList<>(currentParametersWithoutStoreSelectionParameters);

        if (storeSelection != null && storeSelection.getStoreProjection() != null) {
            addParamIfNotNull(resultingParameters, STORE_PROJECTION, storeSelection.getStoreProjection().toString());
        }
        return resultingParameters;
    }

    @Nullable
    public static StoreSelection extractStoreSelectionFromHttpQueryParameters(final List<NameValuePair> additionalHttpQueryParameters) {
        final List<NameValuePair> storeSelectionCandidates = additionalHttpQueryParameters
                .stream()
                .filter(pair -> STORE_PROJECTION.equals(pair.getName()))
                .collect(Collectors.toList());
        final boolean containsStoreSelection = storeSelectionCandidates.stream()
                .anyMatch(pair -> STORE_PROJECTION.equals(pair.getName()));
        return containsStoreSelection ? extractStoreSelection(storeSelectionCandidates) : null;
    }

    private static StoreSelection extractStoreSelection(final List<NameValuePair> storeSelectionCandidates) {
        final Map<String, String> map = NameValuePair.convertToStringMap(storeSelectionCandidates);
        return StoreSelectionBuilder.of(map.get(STORE_PROJECTION))
                .build();
    }

    private static void addParamIfNotNull(final List<NameValuePair> resultingParameters, final String name, final String value) {
        if (value != null) {
            resultingParameters.add(NameValuePair.of(name, value));
        }
    }
}
