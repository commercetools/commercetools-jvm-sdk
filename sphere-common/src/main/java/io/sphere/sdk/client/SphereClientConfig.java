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
}
