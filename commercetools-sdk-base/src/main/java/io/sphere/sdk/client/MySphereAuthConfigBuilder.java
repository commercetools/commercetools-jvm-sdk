package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.List;

import static io.sphere.sdk.client.ClientPackage.transformEnumScopeListToStringList;

public final class MySphereAuthConfigBuilder extends Base implements Builder<SphereAuthConfig> {

    private String projectKey;
    private String clientId;
    private String clientSecret;
    private String authUrl = ClientPackage.AUTH_URL;
    private List<String> scopes = ClientPackage.DEFAULT_SCOPES;

    private MySphereAuthConfigBuilder() {
    }

    public static MySphereAuthConfigBuilder ofKeyIdSecret(final String projectKey, final String clientId, final String clientSecret) {
        final MySphereAuthConfigBuilder builder = new MySphereAuthConfigBuilder();
        builder.clientId = clientId;
        builder.clientSecret = clientSecret;
        builder.projectKey = projectKey;
        return builder;
    }

    public static MySphereAuthConfigBuilder ofAuthConfig(final SphereAuthConfig template) {
        return ofKeyIdSecret(template.getProjectKey(), template.getClientId(), template.getClientSecret())
                .authUrl(template.getAuthUrl())
                .scopeStrings(template.getScopes());
    }

    public MySphereAuthConfigBuilder authUrl(final String authUrl) {
        this.authUrl = authUrl;
        return this;
    }

    public MySphereAuthConfigBuilder scopes(final List<SphereScope> scopes) {
        return scopeStrings(transformEnumScopeListToStringList(scopes));
    }

    public MySphereAuthConfigBuilder scopeStrings(final List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    @Override
    public SphereAuthConfig build() {
        return new MySphereAuthConfigImpl(projectKey, clientId, clientSecret, authUrl, scopes);
    }
}