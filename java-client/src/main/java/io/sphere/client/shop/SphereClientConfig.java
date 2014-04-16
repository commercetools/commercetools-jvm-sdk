package io.sphere.client.shop;

import com.google.common.collect.Sets;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.sphere.client.SphereClientException;
import net.jcip.annotations.*;
import java.util.Locale;
import static io.sphere.client.shop.ConfigurationKeys.*;

/** The configuration for a {@link SphereClient}.
 *
 * To create a config, use
 * <pre>
 * {@code
 * new SphereClientConfig.Builder("my-project", "my-clientId", "my-clientSecret").setApiMode(...).build()
 * }
 * </pre>
 * */
@Immutable
final public class SphereClientConfig {
    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final ApiMode apiMode;
    private final String coreHttpServiceUrl;
    private final String authHttpServiceUrl;
    private final Locale defaultLocale;

    private SphereClientConfig(Builder builder) {
        validateProjectKey(builder.projectKey);
        this.projectKey = builder.projectKey;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.apiMode = builder.apiMode;
        this.coreHttpServiceUrl = builder.coreHttpServiceUrl;
        this.authHttpServiceUrl = builder.authHttpServiceUrl;
        this.defaultLocale = builder.defaultLocale;
    }

    public static boolean isValidProjectKey(String projectKey) {
        return projectKey.matches("[a-zA-Z0-9_-]+");
    }

    public static void validateProjectKey(String projectKey) {
        if (!isValidProjectKey(projectKey)) {
            throw new SphereClientException(
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
    public String getCoreHttpServiceUrl() { return this.coreHttpServiceUrl; }
    /** Sphere OAuth2 endpoint. */
    public String getAuthHttpServiceUrl() { return this.authHttpServiceUrl; }

    /** The default Locale for this project. Note that this can be be overridden for many methods. */
    public Locale getDefaultLocale() { return this.defaultLocale; }

    @Override
    public String toString() {
        return "SphereClientConfig{" +
                "projectKey='" + projectKey + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", apiMode=" + apiMode +
                ", coreHttpServiceUrl='" + coreHttpServiceUrl + '\'' +
                ", authHttpServiceUrl='" + authHttpServiceUrl + '\'' +
                ", defaultLocale=" + defaultLocale +
                '}';
    }

    @NotThreadSafe
    public static class Builder {
        private String projectKey;
        private String clientId;
        private String clientSecret;
        private ApiMode apiMode;
        private String coreHttpServiceUrl;
        private String authHttpServiceUrl;
        private final Locale defaultLocale;

        public Builder(String projectKey, String clientId, String clientSecret, Locale defaultLocale) {
            this(projectKey, clientId, clientSecret, defaultLocale, ConfigFactory.load().getConfig("sphere"));
        }

        public Builder(String projectKey, String clientId, String clientSecret, Locale defaultLocale, Config conf) {
            this.projectKey = projectKey;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            this.defaultLocale = defaultLocale;
            coreHttpServiceUrl = conf.getString(CORE_URL);
            authHttpServiceUrl = conf.getString(AUTH_URL);
            final String apiModeAsString = conf.getString(PRODUCTS_API_MODE);
            apiMode = Sets.newHashSet("Staged", "staged").contains(apiModeAsString) ? ApiMode.Staged : ApiMode.Published;
        }

        public Builder setClientId(String clientId) { this.clientId = clientId; return this; }
        public Builder setClientSecret(String clientSecret) { this.clientSecret = clientSecret; return this; }
        public Builder setProjectKey(String projectKey) { this.projectKey = projectKey; return this; }
        public Builder setApiMode(ApiMode apiMode) { this.apiMode = apiMode; return this; }
        public Builder setCoreHttpServiceUrl(String url) { this.coreHttpServiceUrl = url; return this; }
        public Builder setAuthHttpServiceUrl(String url) { this.authHttpServiceUrl = url; return this; }

        public SphereClientConfig build() { return new SphereClientConfig(this); }

        @Override
        public String toString() {
            return "Builder{" +
                    "projectKey='" + projectKey + '\'' +
                    ", clientId='" + clientId + '\'' +
                    ", clientSecret='" + clientSecret + '\'' +
                    ", apiMode=" + apiMode +
                    ", coreHttpServiceUrl='" + coreHttpServiceUrl + '\'' +
                    ", authHttpServiceUrl='" + authHttpServiceUrl + '\'' +
                    ", defaultLocale=" + defaultLocale +
                    '}';
        }
    }
}
