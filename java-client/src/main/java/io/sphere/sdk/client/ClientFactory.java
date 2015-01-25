package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.function.Function;

/**
 * A factory to instantiate SPHERE.IO clients.
 *
 * {@link #createHttpTestDouble(java.util.function.Function)} and
 * {@link #createObjectTestDouble(java.util.function.Function)} are basic
 * methods to provide a test double for a client. If you need more specific behavior implement
 * {@link io.sphere.sdk.client.SphereClient}.
 *
 * @param <T> the type of the client to instantiate
 * @see SphereClientFactory
 */
interface ClientFactory<T> {

    /**
     * Creates a standard client with configurable service URLs. Intended for commercetools staff
     * developing with a custom SPHERE.IO instance.
     *
     * @param config configuration for the client
     * @return client
     */
    T createClient(final SphereClientConfig config);

    /**
     * Creates a standard client suitable for online shops.
     *
     * For the credentials consult <a href="https://admin.sphere.io">the Merchant Center</a>.
     * @param projectKey the project identifier
     * @param clientId username
     * @param clientSecret password
     * @return sphere client
     */
    default T createClient(final String projectKey, final String clientId, final String clientSecret) {
        return createClient(SphereClientConfig.of(projectKey, clientId, clientSecret));
    }

    /**
     * Creates a test double for a SPHERE.IO client which enables to fake http responses from SPHERE.IO.
     * The client does not need an internet connection.
     *
     * {@include.example io.sphere.sdk.client.TestsDemo#withJson()}
     *
     * @param function a function which returns a matching object for a SPHERE.IO request.
     * @return sphere client test double
     */
    T createHttpTestDouble(final Function<HttpRequest, HttpResponse> function);

    /**
     * Creates a test double for a SPHERE.IO client which enables to fake the results of the client as Java object.
     * The client does not need an internet connection.
     *
     * {@include.example io.sphere.sdk.client.TestsDemo#withInstanceResults()}
     * {@include.example io.sphere.sdk.client.TestsDemo#modelInstanceFromJson()}
     *
     * @param function a function which returns a matching http request for a SPHERE.IO request.
     * @return sphere client test double
     */
    T createObjectTestDouble(final Function<HttpRequest, Object> function);
}
