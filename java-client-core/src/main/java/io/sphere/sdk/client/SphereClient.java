package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;

import java.io.Closeable;
import java.util.concurrent.CompletionStage;

/**
 * A client interface to perform requests to SPHERE.IO.
 *
 * <h3 id=instantiation>Instantiation</h3>
 *
 * {@include.example example.JavaClientInstantiationExample}
 *
 * <h3 id=example-call>Example call</h3>
 *
 * {@include.example example.TaxCategoryQueryExample#exampleQuery()}
 *
 * Refer to <a href="../meta/SphereResources.html">resources</a> for known SPHERE.IO requests.
 *
 */
public interface SphereClient extends Closeable {
    <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest);

    void close();

    /**
     * Raw client creation.
     * See also SphereClientFactory.
     *
     * @param config SPHERE.IO project and location
     * @param httpClient client to execute requests
     * @param tokenSupplier delivery of access tokens
     * @return sphere client
     */
    public static SphereClient of(final SphereApiConfig config, final HttpClient httpClient, final SphereAccessTokenSupplier tokenSupplier) {
        return SphereClientImpl.of(config, httpClient, tokenSupplier);
    }
}
