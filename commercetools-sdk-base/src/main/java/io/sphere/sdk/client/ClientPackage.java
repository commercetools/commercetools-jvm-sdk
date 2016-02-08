package io.sphere.sdk.client;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isBlank;

final class ClientPackage {
    public static final String API_URL = "https://api.sphere.io";
    public static final String AUTH_URL = "https://auth.sphere.io";

    public static String requireNonBlank(final String parameter, final String parameterName) {
        if (isBlank(parameter)) {
            throw new IllegalArgumentException(format("%s is null or empty.", parameterName));
        }
        return parameter;
    }
}
