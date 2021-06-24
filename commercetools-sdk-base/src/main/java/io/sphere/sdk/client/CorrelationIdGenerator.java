package io.sphere.sdk.client;

import java.util.function.Supplier;

/**
 * Generates correlation ids for each request.
 *
 * The format of the correlation id is defined as:
 * {@code <projectKey> / <UUID> (/ customId)*}
 */
@FunctionalInterface
public interface CorrelationIdGenerator extends Supplier<String> {

    /**
     * Creates a new correlation id.
     *
     * @see io.sphere.sdk.http.HttpHeaders#X_CORRELATION_ID
     *
     * @return a new unique correlation id
     */
    @Override
    String get();

    /**
     * Creates a new default correlation id generator for the given project key.
     *
     * @param projectKey the project key {@link SphereApiConfig#getProjectKey()}
     * @return the default correlation id provider
     */
    static CorrelationIdGenerator of(final String projectKey) {
        return new CorrelationIdGeneratorImpl(projectKey);
    }
}
