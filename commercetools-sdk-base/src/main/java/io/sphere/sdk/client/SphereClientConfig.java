package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static io.sphere.sdk.client.ClientPackage.*;

/**
 * The full api and auth configuration for a commercetools client.
 *
 * @see SphereClientConfigBuilder
 *
 */
public final class SphereClientConfig extends Base implements SphereAuthConfig, SphereApiConfig, SphereCorrelationIdConfig {
    public static final String ENVIRONMENT_VARIABLE_API_URL_SUFFIX = "API_URL";
    public static final String ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX = "AUTH_URL";
    public static final String ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX = "PROJECT_KEY";
    public static final String ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX = "CLIENT_ID";
    public static final String ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX = "CLIENT_SECRET";
    public static final String ENVIRONMENT_VARIABLE_SCOPES_SUFFIX = "SCOPES";

    public static final String PROPERTIES_KEY_API_URL_SUFFIX = "apiUrl";
    public static final String PROPERTIES_KEY_AUTH_URL_SUFFIX = "authUrl";
    public static final String PROPERTIES_KEY_PROJECT_KEY_SUFFIX = "projectKey";
    public static final String PROPERTIES_KEY_CLIENT_ID_SUFFIX = "clientId";
    public static final String PROPERTIES_KEY_CLIENT_SECRET_SUFFIX = "clientSecret";
    public static final String PROPERTIES_KEY_SCOPES_SUFFIX = "scopes";

    private final String projectKey;
    private final String clientId;
    private final String clientSecret;
    private final String authUrl;
    private final String apiUrl;
    private final List<String> scopes;
    private final CorrelationIdGenerator correlationIdGenerator;

    SphereClientConfig(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String apiUrl, final List<String> scopes, final CorrelationIdGenerator correlationIdGenerator) {
        this.scopes = new ArrayList<>(scopes);
        this.apiUrl = requireNonBlank(apiUrl, "apiUrl");
        this.projectKey = requireNonBlank(projectKey, "projectKey");
        this.clientId = requireNonBlank(clientId, "clientId");
        this.clientSecret = requireNonBlank(clientSecret, "clientSecret");
        this.authUrl = requireNonBlank(authUrl, "authUrl");
        this.correlationIdGenerator = correlationIdGenerator;
    }

    public static SphereClientConfig of(final String projectKey, final String clientId, final String clientSecret) {
        return of(projectKey, clientId, clientSecret, AUTH_URL, API_URL);
    }

    public static SphereClientConfig of(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String apiUrl) {
        return new SphereClientConfig(projectKey, clientId, clientSecret, authUrl, apiUrl, ClientPackage.DEFAULT_SCOPES, CorrelationIdGenerator.of(projectKey));
    }

    public static SphereClientConfig of(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String apiUrl, final CorrelationIdGenerator correlationIdGenerator) {
        return new SphereClientConfig(projectKey, clientId, clientSecret, authUrl, apiUrl, ClientPackage.DEFAULT_SCOPES, correlationIdGenerator);
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String getAuthUrl() {
        return authUrl;
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
    public String getProjectKey() {
        return projectKey;
    }

    @Override
    public CorrelationIdGenerator getCorrelationIdGenerator() {
        return correlationIdGenerator;
    }

    /**
     * Gets the scopes which are permitted.
     *
     * {@include.example io.sphere.sdk.client.SphereClientConfigBuilderTest#scopes()}
     *
     * @return scopes
     */
    @Override
    public List<String> getScopes() {
        return scopes;
    }

    @Override
    public List<String> getRawScopes() {
        return scopes.stream().map(s -> s + ":" + projectKey).collect(Collectors.toList());
    }

    public SphereClientConfig withApiUrl(final String apiUrl) {
        return new SphereClientConfig(getProjectKey(), getClientId(), getClientSecret(), getAuthUrl(), apiUrl, getScopes(), getCorrelationIdGenerator());
    }

    public SphereClientConfig withAuthUrl(final String authUrl) {
        return new SphereClientConfig(getProjectKey(), getClientId(), getClientSecret(), authUrl, getApiUrl(), getScopes(), getCorrelationIdGenerator());
    }

    /**
    Creates a {@link SphereClientConfig} out of environment variables using {@code prefix} as namespace parameter.

    An example environment variable initialization with "EXAMPLE" as {@code prefix}:
    <pre>{@code
    export EXAMPLE_PROJECT_KEY="YOUR project key"
    export EXAMPLE_CLIENT_ID="YOUR client id"
    export EXAMPLE_CLIENT_SECRET="YOUR client secret"
    export EXAMPLE_AUTH_URL="https://auth.europe-west1.gcp.commercetools.com" #optional parameter
    export EXAMPLE_API_URL="https://api.europe-west1.gcp.commercetools.com" #optional parameter
    export EXAMPLE_SCOPES="manage_project" #optional parameter
    #separate multiple elements with a comma
    #export EXAMPLE_SCOPES="manage_customers,view_orders"
    }</pre>

    The possible suffixes are
    {@value #ENVIRONMENT_VARIABLE_PROJECT_KEY_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_CLIENT_ID_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_CLIENT_SECRET_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_AUTH_URL_SUFFIX},
    {@value #ENVIRONMENT_VARIABLE_API_URL_SUFFIX} and
    {@value #ENVIRONMENT_VARIABLE_SCOPES_SUFFIX}.

    @param prefix prefix of the environment variables
    @return a new {@link SphereClientConfig} containing the values of the environment variables.
     */
    public static SphereClientConfig ofEnvironmentVariables(final String prefix) {
        return SphereClientConfigUtils.ofEnvironmentVariables(prefix, System::getenv);
    }

    /**
     Creates a {@link SphereClientConfig} using {@link Properties} using {@code prefix} as namespace parameter.

     An example properties file with "commercetools." (including the dot) as {@code prefix}:
     <pre>{@code
    commercetools.projectKey=YOUR project key without quotes
    commercetools.clientId=YOUR client id without quotes
    commercetools.clientSecret=YOUR client secret without quotes
    #optional:
    commercetools.authUrl=https://auth.europe-west1.gcp.commercetools.com
    commercetools.apiUrl=https://api.europe-west1.gcp.commercetools.com
    commercetools.scopes=manage_project
    #separate multiple scopes with a comma
    #commercetools.scopes=manage_customers,view_orders
    }</pre>

     The possible suffixes are
     {@value #PROPERTIES_KEY_PROJECT_KEY_SUFFIX},
     {@value #PROPERTIES_KEY_CLIENT_ID_SUFFIX},
     {@value #PROPERTIES_KEY_CLIENT_SECRET_SUFFIX},
     {@value #PROPERTIES_KEY_AUTH_URL_SUFFIX},
     {@value #PROPERTIES_KEY_API_URL_SUFFIX} and
     {@value #PROPERTIES_KEY_SCOPES_SUFFIX}.

     @param properties properties containing the commercetools platform credentials
     @param prefix prefix of the property keys
     @return a new {@link SphereClientConfig} containing the values of the properties.
     */
    public static SphereClientConfig ofProperties(final Properties properties, final String prefix) {
        return SphereClientConfigUtils.ofProperties(properties, prefix);
    }
}
