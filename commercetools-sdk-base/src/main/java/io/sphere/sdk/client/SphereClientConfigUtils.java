package io.sphere.sdk.client;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.sphere.sdk.client.ClientPackage.API_URL;
import static io.sphere.sdk.client.ClientPackage.AUTH_URL;
import static io.sphere.sdk.client.SphereClientConfig.*;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

final class SphereClientConfigUtils {
    private SphereClientConfigUtils() {
    }

    public static SphereClientConfig ofEnvironmentVariables(final String prefix, final Function<String, String> getEnvironmentVariable) {
        Objects.requireNonNull(prefix);

        final Map<String, String> configMap = asList(ENVIRONMENT_VARIABLE_API_URL_SUFFIX, ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX, ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX, ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX, ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX, ENVIRONMENT_VARIABLE_SCOPES_SUFFIX).stream()
                .map(suffix -> {
                    final String key = buildEnvKey(prefix, suffix);
                    final String nullableValue = getEnvironmentVariable.apply(key);
                    return new ImmutablePair<>(suffix, nullableValue);
                })
                .filter(pair -> null != pair.getRight())
                .collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));

        final Function<String, String> throwExceptionOnAbsent = key -> throwEnvException(prefix, buildEnvKey(prefix, key));

        final String projectKey = configMap.computeIfAbsent(ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX, throwExceptionOnAbsent);
        final String clientId = configMap.computeIfAbsent(ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX, throwExceptionOnAbsent);
        final String clientSecret = configMap.computeIfAbsent(ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX, throwExceptionOnAbsent);
        final String apiUrl = configMap.getOrDefault(ENVIRONMENT_VARIABLE_API_URL_SUFFIX, API_URL);
        final String authUrl = configMap.getOrDefault(ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX, AUTH_URL);
        final String scopesAsString = configMap.getOrDefault(ENVIRONMENT_VARIABLE_SCOPES_SUFFIX, "");
        final List<SphereScope> scopes = scopesAsCommaSeparatedStringsToList(scopesAsString);

        return SphereClientConfigBuilder.ofKeyIdSecret(projectKey, clientId, clientSecret)
                .apiUrl(apiUrl)
                .authUrl(authUrl)
                .scopes(scopes)
                .build();
    }

    private static String throwEnvException(final String prefix, final String missingKey) {
        throw new IllegalArgumentException(
                "Missing environment variable '" + missingKey + "'.\n" +
                        "Usage:\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX) + "=\"YOUR project key\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX) + "=\"YOUR client id\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX) + "=\"YOUR client secret\"\n" +
                        "#optional:\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_API_URL_SUFFIX) + "=\"https://api.europe-west1.gcp.commercetools.com\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX) + "=\"https://auth.europe-west1.gcp.commercetools.com\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_SCOPES_SUFFIX) + "=\"manage_project\""
        );
    }

    private static String buildEnvKey(final String prefix, final String suffix) {
        return prefix + "_" + suffix;
    }

    private static String buildPropKey(final String prefix, final String suffix) {
        return prefix + suffix;
    }

    public static SphereClientConfig ofProperties(final Properties properties, final String prefix) {
        final String projectKey = extract(properties, prefix, PROPERTIES_KEY_PROJECT_KEY_SUFFIX);
        final String clientId = extract(properties, prefix, PROPERTIES_KEY_CLIENT_ID_SUFFIX);
        final String clientSecret = extract(properties, prefix, PROPERTIES_KEY_CLIENT_SECRET_SUFFIX);
        final String apiUrl = extract(properties, prefix, PROPERTIES_KEY_API_URL_SUFFIX, API_URL);
        final String authUrl = extract(properties, prefix, PROPERTIES_KEY_AUTH_URL_SUFFIX, AUTH_URL);
        final String scopesAsString = extract(properties, prefix, PROPERTIES_KEY_SCOPES_SUFFIX, "");
        final List<SphereScope> scopes = scopesAsCommaSeparatedStringsToList(scopesAsString);
        return SphereClientConfigBuilder.ofKeyIdSecret(projectKey, clientId, clientSecret)
                .apiUrl(apiUrl)
                .authUrl(authUrl)
                .scopes(scopes)
                .build();
    }

    private static List<SphereScope> scopesAsCommaSeparatedStringsToList(final String scopesAsString) {
        return isEmpty(scopesAsString)
                    ? ClientPackage.DEFAULT_PROJECT_SCOPES
                    : Arrays.stream(scopesAsString.split(","))
                    .map(SphereProjectScope::ofScopeString)
                    .collect(Collectors.toList());
    }

    private static String extract(final Properties properties, final String prefix, final String suffix, final String defaultValue) {
        return properties.getProperty(buildPropKey(prefix, suffix), defaultValue);
    }

    private static String extract(final Properties properties, final String prefix, final String suffix) {
        final String mapKey = buildPropKey(prefix, suffix);
        return properties.computeIfAbsent(mapKey, key -> throwPropertiesException(prefix, mapKey)).toString();
    }

    private static String throwPropertiesException(final String prefix, final String missingKey) {
        throw new IllegalArgumentException(
                "Missing property value '" + missingKey + "'.\n" +
                        "Usage:\n" +
                        "" + buildPropKey(prefix, PROPERTIES_KEY_PROJECT_KEY_SUFFIX) + "=YOUR project key\n" +
                        "" + buildPropKey(prefix, PROPERTIES_KEY_CLIENT_ID_SUFFIX) + "=YOUR client id\n" +
                        "" + buildPropKey(prefix, PROPERTIES_KEY_CLIENT_SECRET_SUFFIX) + "=YOUR client secret\n" +
                        "#optional:\n" +
                        "" + buildPropKey(prefix, PROPERTIES_KEY_API_URL_SUFFIX) + "=https://api.europe-west1.gcp.commercetools.com\n" +
                        "" + buildPropKey(prefix, PROPERTIES_KEY_AUTH_URL_SUFFIX) + "=https://auth.europe-west1.gcp.commercetools.com\n" +
                        "" + buildPropKey(prefix, PROPERTIES_KEY_SCOPES_SUFFIX) + "=manage_project" +
                        "#don't use quotes for the property values\n"
        );
    }
}
