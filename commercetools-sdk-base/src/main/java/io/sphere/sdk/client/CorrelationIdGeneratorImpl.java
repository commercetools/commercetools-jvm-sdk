package io.sphere.sdk.client;

import java.util.UUID;

/**
 * Default implementation of {@link CorrelationIdGenerator}.
 */
class CorrelationIdGeneratorImpl implements CorrelationIdGenerator {
    private final String projectKey;

    CorrelationIdGeneratorImpl(final String projectKey) {
        this.projectKey = projectKey;
    }

    @Override
    public String getProjectKey() {
        return projectKey;
    }

    @Override
    public String createCorrelationId() {
        return String.join("/", projectKey, UUID.randomUUID().toString());
    }
}
