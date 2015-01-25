package io.sphere.sdk.client;

/**
 * A factory to instantiate SPHERE.IO clients.
 *
 * @param <T> the type of the client to instantiate
 * @see SphereJavaClientFactory
 */
public interface SphereClientFactory<T> {

    T createClient(final SphereClientConfig config);

    default T createClient(final String projectKey, final String clientId, final String clientSecret) {
        return createClient(SphereClientConfig.of(projectKey, clientId, clientSecret));
    }
}
