package io.sphere.sdk.client;

/**
 * Generates correlation ids for each request.
 *
 * The format of the correlation id is defined as:
 * {@code <projectKey> / <UUID> (/ customId)*}
 */
interface CorrelationIdGenerator {

    String getProjectKey();

    /**
     * Creates a new correlation id.
     *
     * @see io.sphere.sdk.http.HttpHeaders#X_CORRELATION_ID
     *
     * @return returns a new unique correlation id
     */
    String createCorrelationId();

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
