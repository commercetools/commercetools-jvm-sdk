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
    private CorrelationIdGenerator correlationIdGenerator;

    private SphereClientConfigBuilder() {
    }

    public static SphereClientConfigBuilder ofClientConfig(final SphereClientConfig config) {
        return ofKeyIdSecret(config.getProjectKey(), config.getClientId(), config.getClientSecret())
                .authUrl(config.getAuthUrl())
                .apiUrl(config.getApiUrl())
                .scopeStrings(config.getScopes())
                .correlationIdGenerator(config.getCorrelationIdGenerator());
    }

    public static SphereClientConfigBuilder ofKeyIdSecret(final String projectKey, final String clientId, final String clientSecret) {
        final SphereClientConfigBuilder builder = new SphereClientConfigBuilder();
        builder.clientId = clientId;
        builder.clientSecret = clientSecret;
        builder.projectKey = projectKey;
        builder.correlationIdGenerator = CorrelationIdGenerator.of(projectKey);
        return builder;
    }

    public SphereClientConfigBuilder projectKey(final String projectKey) {
        this.projectKey = projectKey;
        return this;
    }

    public SphereClientConfigBuilder clientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    public SphereClientConfigBuilder clientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
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
        return scopeStrings(transformEnumScopeListToStringList(scopes));
    }

    public SphereClientConfigBuilder scopeStrings(final List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    public SphereClientConfigBuilder correlationIdGenerator(final CorrelationIdGenerator correlationIdGenerator) {
        this.correlationIdGenerator = correlationIdGenerator;
        return this;
    }

    @Override
    public SphereClientConfig build() {
        return new SphereClientConfig(projectKey, clientId, clientSecret, authUrl, apiUrl, scopes, correlationIdGenerator);
    }
}
