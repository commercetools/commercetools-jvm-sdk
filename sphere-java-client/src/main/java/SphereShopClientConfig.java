package de.commercetools.sphere.client;

/** The configuration for a {@link SphereShopClient}. */
@net.jcip.annotations.ThreadSafe
final public class SphereShopClientConfig implements SphereClientConfig {
    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final String coreHttpServiceUrl;
    private final String authHttpServiceUrl;

    private SphereShopClientConfig(Builder builder) {
        this.projectKey = builder.projectKey;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.coreHttpServiceUrl = builder.coreHttpServiceUrl;
        this.authHttpServiceUrl = builder.authHttpServiceUrl;
    }

    /** Gets the key of the Sphere project accessed by this client. */
    public String getProjectKey() { return this.projectKey; }
    /**
     * Gets the unique client ID that, together with the client secret, authorizes the
     * shop client to access the underlying Sphere project.
     */
    public String getClientId() { return this.clientId; }
    /**
     * Gets the confidential client secret that, together with the client ID, authorizes the
     * shop client to access the underlying Sphere project.
     */
    public String getClientSecret() { return this.clientSecret; }

    @Override public String getCoreHttpServiceUrl() { return this.coreHttpServiceUrl; }
    @Override public String getAuthHttpServiceUrl() { return this.authHttpServiceUrl; }

    @net.jcip.annotations.NotThreadSafe
    public static class Builder {
        private String projectKey;
        private String clientId;
        private String clientSecret;
        private String coreHttpServiceUrl;
        private String authHttpServiceUrl;

        public Builder(String projectKey, String clientId, String clientSecret) {
            this.projectKey = projectKey;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.coreHttpServiceUrl = "http://api.sphere.com/";
            this.authHttpServiceUrl = "http://auth.sphere.com/";
        }

        public Builder setClientId(String clientId) { this.clientId = clientId; return this; }
        public Builder setClientSecret(String clientId) { this.clientSecret = clientSecret; return this; }
        public Builder setProjectKey(String clientId) { this.projectKey = projectKey; return this; }
        public Builder setCoreHttpServiceUrl(String url) { this.coreHttpServiceUrl = url; return this; }
        public Builder setAuthHttpServiceUrl(String url) { this.authHttpServiceUrl = url; return this; }

        public SphereShopClientConfig build() { return new SphereShopClientConfig(this); }
    }
}