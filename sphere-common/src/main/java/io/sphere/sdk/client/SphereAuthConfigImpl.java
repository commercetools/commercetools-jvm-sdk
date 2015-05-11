package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.client.ClientPackage.requireNonBlank;

final class SphereAuthConfigImpl extends Base implements SphereAuthConfig {
    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final String authUrl;

    SphereAuthConfigImpl(final String projectKey, final String clientId, final String clientSecret, final String authUrl) {
        this.projectKey = requireNonBlank(projectKey, "projectKey");
        this.clientId = requireNonBlank(clientId, "clientId");
        this.clientSecret = requireNonBlank(clientSecret, "clientSecret");
        this.authUrl = requireNonBlank(authUrl, "authUrl");
    }

    @Override
    public String getProjectKey() {
        return projectKey;
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
    public String getAuthUrl() {
        return authUrl;
    }
}
