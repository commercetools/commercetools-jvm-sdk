package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

final class SphereAuthConfigImpl extends Base implements SphereAuthConfig {
    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final String authUrl;

    SphereAuthConfigImpl(final String projectKey, final String clientId, final String clientSecret, final String authUrl) {
        this.projectKey = projectKey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authUrl = authUrl;
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
