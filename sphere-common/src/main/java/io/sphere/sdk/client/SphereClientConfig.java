package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.client.ClientPackage.API_URL;
import static io.sphere.sdk.client.ClientPackage.AUTH_URL;
import static io.sphere.sdk.client.ClientPackage.requireNonBlank;

/**
 * The full configuration for a SPHERE.IO client.
 *
 */
public class SphereClientConfig extends Base implements SphereAuthConfig, SphereApiConfig {
    public static final String ENVIRONMENT_VARIABLE_API_URL_SUFFIX = "API_URL";
    public static final String ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX = "AUTH_URL";
    public static final String ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX = "PROJECT_KEY";
    public static final String ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX = "CLIENT_ID";
    public static final String ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX = "CLIENT_SECRET";

    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final String authUrl;
    private final String apiUrl;

    private SphereClientConfig(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String apiUrl) {
        this.apiUrl = requireNonBlank(apiUrl, "apiUrl");
        this.projectKey = requireNonBlank(projectKey, "projectKey");
        this.clientId = requireNonBlank(clientId, "clientId");
        this.clientSecret = requireNonBlank(clientSecret, "clientSecret");
        this.authUrl = requireNonBlank(authUrl, "authUrl");
    }

    public static SphereClientConfig of(final String projectKey, final String clientId, final String clientSecret) {
        return of(projectKey, clientId, clientSecret, AUTH_URL, API_URL);
    }

    public static SphereClientConfig of(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String apiUrl) {
        return new SphereClientConfig(projectKey, clientId, clientSecret, authUrl, apiUrl);
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String getAuthUrl() {
        return authUrl;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getProjectKey() {
        return projectKey;
    }

    public SphereClientConfig withApiUrl(final String apiUrl) {
        return of(getProjectKey(), getClientId(), getClientSecret(), getAuthUrl(), apiUrl);
    }
    
    public SphereClientConfig withAuthUrl(final String authUrl) {
        return of(getProjectKey(), getClientId(), getClientSecret(), authUrl, getApiUrl());
    }

    /**
    Creates a {@link SphereClientConfig} out of environment variables using {@code prefix} as namespace parameter.

    An example environment variable initialization with "EXAMPLE" as {@code prefix}:
    <pre>{@code
    export EXAMPLE_PROJECT_KEY="YOUR project key"
    export EXAMPLE_CLIENT_ID="YOUR client id"
    export EXAMPLE_CLIENT_SECRET="YOUR client secret"
    }</pre>

    The possible suffixes are
    {@value #ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX} and
    {@value #ENVIRONMENT_VARIABLE_API_URL_SUFFIX}.

    @param prefix prefix of the environment variables
    @return a new {@link SphereClientConfig} containing the values of the environment variables.
     */
    public static SphereClientConfig ofEnvironmentVariables(final String prefix) {
        return SphereClientConfigUtils.ofEnvironmentVariables(prefix, System::getenv);
    }
}
