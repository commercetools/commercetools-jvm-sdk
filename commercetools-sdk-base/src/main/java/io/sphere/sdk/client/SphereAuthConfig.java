package io.sphere.sdk.client;

import static io.sphere.sdk.client.ClientPackage.AUTH_URL;

/**
 * Contains the configuration to fetch access keys for SPHERE.IO.
 */
public interface SphereAuthConfig {
    String getAuthUrl();

    String getClientId();

    String getClientSecret();

    String getProjectKey();

    static SphereAuthConfig of(final String projectKey, final String clientId, final String clientSecret) {
        return of(projectKey, clientId, clientSecret, AUTH_URL);
    }

    static SphereAuthConfig of(final String projectKey, final String clientId, final String clientSecret, final String authUrl) {
        return new SphereAuthConfigImpl(projectKey, clientId, clientSecret, authUrl);
    }
}
