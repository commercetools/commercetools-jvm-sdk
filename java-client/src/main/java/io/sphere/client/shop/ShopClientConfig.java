package io.sphere.client.shop;

import io.sphere.client.SphereClientConfig;
import io.sphere.client.SphereException;
import io.sphere.internal.Defaults;
import net.jcip.annotations.*;

/** The configuration for a {@link ShopClient}.
 *
 * To create a config, use
 * <pre>
 * {@code
 * new ShopClientConfig.Builder("my-project", "my-clientId", "my-clientSecret").setApiMode(...).build()
 * }
 * </pre>
 * */
@Immutable
final public class ShopClientConfig implements SphereClientConfig {
    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final ApiMode apiMode;
    private final String coreHttpServiceUrl;
    private final String authHttpServiceUrl;

    private ShopClientConfig(Builder builder) {
        validateProjectKey(builder.projectKey);
        this.projectKey = builder.projectKey;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.apiMode = builder.apiMode;
        this.coreHttpServiceUrl = builder.coreHttpServiceUrl;
        this.authHttpServiceUrl = builder.authHttpServiceUrl;
    }

    public static boolean isValidProjectKey(String projectKey) {
        return projectKey.matches("[a-zA-Z0-9_-]+");
    }

    public static void validateProjectKey(String projectKey) {
        if (!isValidProjectKey(projectKey)) {
            throw new SphereException(
                "Invalid project key: '" + projectKey + "'. " +
                "Project keys can contain alphanumeric characters, dashes and underscores.");
        }
    }

    /** The key of the Sphere project accessed by this client. */
    public String getProjectKey() { return this.projectKey; }
    /** The unique client ID that, together with the client secret, authorizes the
      * shop client to access the underlying Sphere project. */
    public String getClientId() { return this.clientId; }
    /** The confidential client secret that, together with the client ID, authorizes the
      * shop client to access the underlying Sphere project. */
    public String getClientSecret() { return this.clientSecret; }
    /** Specifies whether {@linkplain ApiMode staging or live} data is accessed by the shop client. */
    public ApiMode getApiMode() { return apiMode; }

    /** Sphere HTTP API endpoint. */
    @Override public String getCoreHttpServiceUrl() { return this.coreHttpServiceUrl; }
    /** Sphere OAuth2 endpoint. */
    @Override public String getAuthHttpServiceUrl() { return this.authHttpServiceUrl; }

    @NotThreadSafe
    public static class Builder {
        private String projectKey;
        private String clientId;
        private String clientSecret;
        private ApiMode apiMode = Defaults.apiMode;
        private String coreHttpServiceUrl = Defaults.coreHttpServiceUrl;
        private String authHttpServiceUrl = Defaults.authHttpServiceUrl;

        public Builder(String projectKey, String clientId, String clientSecret) {
            this.projectKey = projectKey;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
        }

        public Builder setClientId(String clientId) { this.clientId = clientId; return this; }
        public Builder setClientSecret(String clientSecret) { this.clientSecret = clientSecret; return this; }
        public Builder setProjectKey(String projectKey) { this.projectKey = projectKey; return this; }
        public Builder setApiMode(ApiMode apiMode) { this.apiMode = apiMode; return this; }
        public Builder setCoreHttpServiceUrl(String url) { this.coreHttpServiceUrl = url; return this; }
        public Builder setAuthHttpServiceUrl(String url) { this.authHttpServiceUrl = url; return this; }

        public ShopClientConfig build() { return new ShopClientConfig(this); }
    }
}
