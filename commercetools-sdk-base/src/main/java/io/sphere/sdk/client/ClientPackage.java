package io.sphere.sdk.client;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.isBlank;

final class ClientPackage {
    public static final String API_URL = "https://api.europe-west1.gcp.commercetools.com";
    public static final String AUTH_URL = "https://auth.europe-west1.gcp.commercetools.com";
    public static final List<SphereScope> DEFAULT_PROJECT_SCOPES = singletonList(SphereProjectScope.MANAGE_PROJECT);
    public static final List<String> DEFAULT_SCOPES = transformEnumScopeListToStringList(DEFAULT_PROJECT_SCOPES);

    public static String requireNonBlank(final String parameter, final String parameterName) {
        if (isBlank(parameter)) {
            throw new IllegalArgumentException(format("%s is null or empty.", parameterName));
        }
        return parameter;
    }

    public static List<String> transformEnumScopeListToStringList(final List<SphereScope> scopes) {
        return Collections.unmodifiableList(scopes.stream()
                .map(scope -> scope.toScopeString())
                .collect(Collectors.toList()));
    }

    public static List<SphereScope> transformStringListToEnumScopeList(final List<String> scopes) {
        return Collections.unmodifiableList(scopes.stream()
                .map(SphereProjectScope::ofScopeString)
                .collect(Collectors.toList()));
    }
}
