package io.sphere.sdk.client;

import static io.sphere.sdk.client.ClientPackage.API_URL;

/**
 * Configuration to make request for a platform web service, does not include authentication configuration or tokens.
 */
public interface SphereApiConfig extends SphereCorrelationIdConfig {
    String getApiUrl();

    String getProjectKey();

    static SphereApiConfig of(final String projectKey) {
        return of(projectKey, API_URL);
    }

    static SphereApiConfig of(final String projectKey, final String apiUrl) {
        return new SphereApiConfigImpl(projectKey, apiUrl, CorrelationIdGenerator.of(projectKey));
    }

    static SphereApiConfig of(final String projectKey, final String apiUrl, final CorrelationIdGenerator correlationIdGenerator) {
        return new SphereApiConfigImpl(projectKey, apiUrl, correlationIdGenerator);
    }
}
