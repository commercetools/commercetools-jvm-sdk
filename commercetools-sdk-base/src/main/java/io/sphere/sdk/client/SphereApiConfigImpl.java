package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.client.ClientPackage.requireNonBlank;

final class SphereApiConfigImpl extends Base implements SphereApiConfig, SphereCorrelationIdConfig {
    private final String apiUrl;
    private final String projectKey;
    private final CorrelationIdGenerator correlationIdGenerator;

    SphereApiConfigImpl(final String projectKey, final String apiUrl, final CorrelationIdGenerator correlationIdGenerator) {
        this.apiUrl = requireNonBlank(apiUrl, "apiUrl");
        this.projectKey = requireNonBlank(projectKey, "projectKey");
        this.correlationIdGenerator = correlationIdGenerator;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String getProjectKey() {
        return projectKey;
    }

    @Override
    public CorrelationIdGenerator getCorrelationIdGenerator() {
        return correlationIdGenerator;
    }
}
