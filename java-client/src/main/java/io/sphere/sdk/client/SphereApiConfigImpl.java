package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

final class SphereApiConfigImpl extends Base implements SphereApiConfig {
    private final String apiUrl;
    private final String projectKey;

    SphereApiConfigImpl(final String projectKey, final String apiUrl) {
        this.apiUrl = apiUrl;
        this.projectKey = projectKey;
    }

    @Override
    public String getApiUrl() {
        return apiUrl;
    }

    @Override
    public String getProjectKey() {
        return projectKey;
    }
}
