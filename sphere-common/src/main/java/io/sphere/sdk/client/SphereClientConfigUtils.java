package io.sphere.sdk.client;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.sphere.sdk.client.SphereClientConfig.*;
import static io.sphere.sdk.client.ClientPackage.*;
import static java.util.Arrays.asList;

final class SphereClientConfigUtils {
    private SphereClientConfigUtils() {
    }

    public static SphereClientConfig ofEnvironmentVariables(final String prefix, final Function<String, String> getEnvironmentVariable) {
        Objects.requireNonNull(prefix);

        final Map<String, String> configMap = asList(ENVIRONMENT_VARIABLE_API_URL_SUFFIX, ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX, ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX, ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX, ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX).stream()
                .map(suffix -> {
                    final String key = buildEnvKey(prefix, suffix);
                    final String nullableValue = getEnvironmentVariable.apply(key);
                    return new ImmutablePair<>(suffix, nullableValue);
                })
                .filter(pair -> null != pair.getRight())
                .collect(Collectors.toMap(ImmutablePair::getLeft, ImmutablePair::getRight));

        final Function<String, String> throwExceptionOnAbsent = key -> throwException(prefix, buildEnvKey(prefix, key));

        final String projectKey = configMap.computeIfAbsent(ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX, throwExceptionOnAbsent);
        final String clientId = configMap.computeIfAbsent(ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX, throwExceptionOnAbsent);
        final String clientSecret = configMap.computeIfAbsent(ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX, throwExceptionOnAbsent);
        final String apiUrl = configMap.getOrDefault(ENVIRONMENT_VARIABLE_API_URL_SUFFIX, API_URL);
        final String authUrl = configMap.getOrDefault(ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX, AUTH_URL);

        return SphereClientConfig.of(projectKey, clientId, clientSecret).withApiUrl(apiUrl).withAuthUrl(authUrl);
    }

    private static String throwException(final String prefix, final String missingKey) {
        throw new IllegalArgumentException(
                "Missing environment variable '" + missingKey + "'.\n" +
                        "Usage:\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX) + "=\"YOUR project key\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX) + "=\"YOUR client id\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX) + "=\"YOUR client secret\"\n" +
                        "#optional:\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_API_URL_SUFFIX) + "=\"https://api.sphere.io\"\n" +
                        "export " + buildEnvKey(prefix, ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX) + "=\"https://auth.sphere.io\""
        );
    }

    private static String buildEnvKey(final String prefix, final String suffix) {
        return prefix + "_" + suffix;
    }
}
