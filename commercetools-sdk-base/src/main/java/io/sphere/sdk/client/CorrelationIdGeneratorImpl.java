package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;

import java.util.UUID;

/**
 * Default implementation of {@link CorrelationIdGenerator}.
 */
final class CorrelationIdGeneratorImpl extends Base implements CorrelationIdGenerator {
    private final String projectKey;

    CorrelationIdGeneratorImpl(final String projectKey) {
        this.projectKey = projectKey;
    }

    public String getProjectKey() {
        return projectKey;
    }

    @Override
    public String get() {
        return String.join("/", projectKey, UUID.randomUUID().toString());
    }
}
