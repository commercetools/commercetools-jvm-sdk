package io.sphere.sdk.products.queries;

import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.stores.Store;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class ProductProjectionQueryParameters extends Base {

    public static final String STORE_PROJECTION = "storeProjection";
    public static final String LOCALE_PROJECTION = "localeProjection";
    public static final List<String> ALL_PARAMETERS = asList(STORE_PROJECTION, LOCALE_PROJECTION);

    public static List<NameValuePair> getQueryParametersWithStoreProjection(@Nullable final Store storeProjection, final List<NameValuePair> currentParameters) {
        final List<NameValuePair> currentParametersWithoutStoreProjectionParameters = currentParameters.stream()
                .filter(pair -> !ALL_PARAMETERS.contains(pair.getName()))
                .collect(toList());
        final List<NameValuePair> resultingParameters = new LinkedList<>(currentParametersWithoutStoreProjectionParameters);

        if (storeProjection != null) {
            addParamIfNotNull(resultingParameters, STORE_PROJECTION, storeProjection.getKey());
        }
        return resultingParameters;
    }

    public static List<NameValuePair> getQueryParametersWithLocaleProjection(@Nullable final List<String> localeProjection, final List<NameValuePair> currentParameters) {
        final List<NameValuePair> currentParametersWithoutLocaleProjectionParameters = currentParameters.stream()
                .filter(pair -> !ALL_PARAMETERS.contains(pair.getName()))
                .collect(toList());
        final List<NameValuePair> resultingParameters = new LinkedList<>(currentParametersWithoutLocaleProjectionParameters);

        if (localeProjection != null) {
            addParamIfNotNull(resultingParameters, LOCALE_PROJECTION, localeProjection.get(0));
        }
        return resultingParameters;
    }

    @Nullable
    public static ProductProjectionQuery extractStoreProjectionFromHttpQueryParameters(final List<NameValuePair> additionalHttpQueryParameters) {
        final List<NameValuePair> storeProjectionCandidates = additionalHttpQueryParameters
                .stream()
                .filter(pair -> ALL_PARAMETERS.contains(pair.getName()))
                .collect(Collectors.toList());
        final boolean containsStoreProjection = storeProjectionCandidates.stream()
                .anyMatch(pair -> STORE_PROJECTION.equals(pair.getName()));
        return containsStoreProjection ? extractStoreProjection(storeProjectionCandidates) : null;
    }

    @Nullable
    public static ProductProjectionQuery extractLocaleProjectionFromHttpQueryParameters(final List<NameValuePair> additionalHttpQueryParameters) {
        final List<NameValuePair> localeProjectionCandidates = additionalHttpQueryParameters
                .stream()
                .filter(pair -> ALL_PARAMETERS.contains(pair.getName()))
                .collect(Collectors.toList());
        final boolean containsLocaleProjection = localeProjectionCandidates.stream()
                .anyMatch(pair -> LOCALE_PROJECTION.equals(pair.getName()));
        return containsLocaleProjection ? extractLocaleProjection(localeProjectionCandidates) : null;
    }

    private static ProductProjectionQuery extractLocaleProjection(final List<NameValuePair> localeProjectionCandidates) {
        final Map<String, String> map = NameValuePair.convertToStringMap(localeProjectionCandidates);
        return ProductProjectionQueryBuilder.ofCurrent()
                .localeProjection(map.get(LOCALE_PROJECTION))
                .build();
    }

    private static ProductProjectionQuery extractStoreProjection(final List<NameValuePair> storeProjectionCandidates) {
        final Map<String, String> map = NameValuePair.convertToStringMap(storeProjectionCandidates);
        return ProductProjectionQueryBuilder.ofCurrent()
                .localeProjection(map.get(STORE_PROJECTION))
                .build();
    }

    private static void addParamIfNotNull(final List<NameValuePair> resultingParameters, final String name, final String value) {
        if (value != null) {
            resultingParameters.add(NameValuePair.of(name, value));
        }
    }
}
