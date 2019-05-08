package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.List;

import static io.sphere.sdk.client.ClientPackage.requireNonBlank;

final class MySphereAuthConfigImpl extends Base implements SphereAuthConfig {

    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final String authUrl;
    private final List<String> scopes;

    MySphereAuthConfigImpl(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final List<String> scopes) {
        this.scopes = scopes;
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

    @Override
    public List<String> getScopes() {
        return scopes;
    }

    @Override
    public List<String> getRawScopes() {
        return scopes;
    }
}