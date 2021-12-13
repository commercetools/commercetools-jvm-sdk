package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * A client interface to perform requests to the platform.
 *
 * <h3 id=instantiation>Instantiation</h3>
 *
 * {@include.example example.JavaClientInstantiationExample}
 *
 * <h3 id=example-call>Example call</h3>
 *
 * {@include.example example.TaxCategoryQueryExample#exampleQuery()}
 *
 * Refer to <a href="../meta/SphereResources.html">resources</a> for known platform requests.
 *
 */
public interface SphereClient extends AutoCloseable {
    /**
     * Executes asynchronously a request to commercetools. By default it does not have a timeout.
     * @param sphereRequest request to commercetools to perfom
     * @param <T> type of the result for the request
     * @return future monad which can contain the result or an exception
     */
    <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest);

    /**
     * Shuts down the client to save resources like connections and threads.
     */
    @Override
    void close();

    /**
     * Raw client creation.
     * See also {@link SphereClientFactory}.
     *
     * @param config platform project and location
     * @param httpClient client to execute requests
     * @param tokenSupplier delivery of access tokens
     * @return sphere client
     */
    static SphereClient of(final SphereApiConfig config, final HttpClient httpClient, final SphereAccessTokenSupplier tokenSupplier) {
        return SphereClientImpl.of(config, httpClient, tokenSupplier, config.getCorrelationIdGenerator());
    }

    /**
     * Raw client creation.
     * See also SphereClientFactory.
     *
     * @param config platform project and location
     * @param httpClient client to execute requests
     * @param tokenSupplier delivery of access tokens
     * @return sphere client
     */
    static SphereClient of(final SphereApiConfig config, final HttpClient httpClient, final SphereAccessTokenSupplier tokenSupplier, final List<SolutionInfo> additionalSolutionInfos) {
        return SphereClientImpl.of(config, httpClient, tokenSupplier, config.getCorrelationIdGenerator(), additionalSolutionInfos);
    }

    /**
     * Getter for the SphereApiConfig used for this client
     * @return the configuration used for this client
     * @see SphereApiConfig
     */
    SphereApiConfig getConfig();
}
