package io.sphere.sdk.client;

import static io.sphere.sdk.client.ClientPackage.API_URL;
import static io.sphere.sdk.client.ClientPackage.AUTH_URL;

/**
 * A factory to instantiate SPHERE.IO clients.
 *
 * @param <T> the type of the client to instantiate
 */
public interface ClientFactory<T> {
    default T createClient(final String projectKey, final String clientId, final String clientSecret) {
        return createClient(projectKey, clientId, clientSecret, AUTH_URL, API_URL);
    }

    T createClient(final String projectKey, final String clientId, final String clientSecret, final String authUrl, final String wsUrl);
}
