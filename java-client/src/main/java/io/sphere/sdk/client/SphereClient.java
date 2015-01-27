package io.sphere.sdk.client;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

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
 * @see io.sphere.sdk.client.SphereClientFactory
 */
public interface SphereClient extends Closeable {
    <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest);

    void close();
}
