package io.sphere.sdk.client;

import java.util.UUID;
import java.util.function.Supplier;

import static io.sphere.sdk.client.ClientPackage.API_URL;

/**
 * Configuration to make request for a platform web service, does not include authentication configuration or tokens.
 */
public interface SphereApiConfig {
    String getApiUrl();

    String getProjectKey();

    Supplier<String> getCorrelationIdGenerator();

    static SphereApiConfig of(final String projectKey) {
        return of(projectKey, API_URL);
    }

    static SphereApiConfig of(final String projectKey, final String apiUrl) {
        return of(projectKey, apiUrl, () -> String.format("%s/%s", projectKey, UUID.randomUUID()));
    }

    static SphereApiConfig of(final String projectKey, final String apiUrl, final Supplier<String> correlationIdGenerator) {
        return new SphereApiConfigImpl(projectKey, apiUrl, correlationIdGenerator);
    }
}
