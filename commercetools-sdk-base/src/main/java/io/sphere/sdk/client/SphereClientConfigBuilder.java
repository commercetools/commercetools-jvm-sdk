package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.List;

import static io.sphere.sdk.client.ClientPackage.transformEnumScopeListToStringList;

public final class SphereClientConfigBuilder extends Base implements Builder<SphereClientConfig> {
    private String projectKey;
    private String clientId;
    private String clientSecret;
    private String authUrl = ClientPackage.AUTH_URL;
    private String apiUrl = ClientPackage.API_URL;
    private List<String> scopes = ClientPackage.DEFAULT_SCOPES;

    private SphereClientConfigBuilder() {
    }

    public static SphereClientConfigBuilder ofKeyIdSecret(final String projectKey, final String clientId, final String clientSecret) {
        final SphereClientConfigBuilder builder = new SphereClientConfigBuilder();
        builder.clientId = clientId;
        builder.clientSecret = clientSecret;
        builder.projectKey = projectKey;
        return builder;
    }

    public SphereClientConfigBuilder apiUrl(final String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }
    
    public SphereClientConfigBuilder authUrl(final String authUrl) {
        this.authUrl = authUrl;
        return this;
    }

    public SphereClientConfigBuilder scopes(final List<SphereScope> scopes) {
        this.scopes = transformEnumScopeListToStringList(scopes);
        return this;
    }

    @Override
    public SphereClientConfig build() {
        return new SphereClientConfig(projectKey, clientId, clientSecret, authUrl, apiUrl, scopes);
    }
}
