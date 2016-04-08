package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.List;

import static io.sphere.sdk.client.ClientPackage.transformEnumScopeListToStringList;

public final class SphereAuthConfigBuilder extends Base implements Builder<SphereAuthConfig> {
    private String projectKey;
    private String clientId;
    private String clientSecret;
    private String authUrl = ClientPackage.AUTH_URL;
    private List<String> scopes = ClientPackage.DEFAULT_SCOPES;

    private SphereAuthConfigBuilder() {
    }

    public static SphereAuthConfigBuilder ofKeyIdSecret(final String projectKey, final String clientId, final String clientSecret) {
        final SphereAuthConfigBuilder builder = new SphereAuthConfigBuilder();
        builder.clientId = clientId;
        builder.clientSecret = clientSecret;
        builder.projectKey = projectKey;
        return builder;
    }

    public static SphereAuthConfigBuilder ofAuthConfig(final SphereAuthConfig template) {
        return ofKeyIdSecret(template.getProjectKey(), template.getClientId(), template.getClientSecret())
                .authUrl(template.getAuthUrl())
                .scopeStrings(template.getScopes());
    }

    public SphereAuthConfigBuilder authUrl(final String authUrl) {
        this.authUrl = authUrl;
        return this;
    }

    public SphereAuthConfigBuilder scopes(final List<SphereScope> scopes) {
        return scopeStrings(transformEnumScopeListToStringList(scopes));
    }

    public SphereAuthConfigBuilder scopeStrings(final List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

    @Override
    public SphereAuthConfig build() {
        return new SphereAuthConfigImpl(projectKey, clientId, clientSecret, authUrl, scopes);
    }
}
