package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import static io.sphere.sdk.client.ClientPackage.requireNonBlank;

final class SphereApiConfigImpl extends Base implements SphereApiConfig {
    private final String apiUrl;
    private final String projectKey;

    SphereApiConfigImpl(final String projectKey, final String apiUrl) {
        this.apiUrl = requireNonBlank(apiUrl, "apiUrl");
        this.projectKey = requireNonBlank(projectKey, "projectKey");
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
