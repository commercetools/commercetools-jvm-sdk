package io.sphere.sdk.client;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * A client for commercetools which provides thread block and non-blocking methods to execute a request.
 *
 * The implementations should be thread-safe so they can be used by multiple threads in parallel.
 */
public interface BlockingSphereClient extends SphereClient {

    @Override
    void close();

    @Override
    <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest);

    /**
     * Executes a request and blocks the caller thread until the response is available or a client specific timeout occurs.
     *
     * @param sphereRequest request to commercetools to perfom
     * @param <T> type of the result for the request
     * @return result for the request to commercetools
     * @throws SphereTimeoutException if a timeout occurs
     */
    <T> T executeBlocking(final SphereRequest<T> sphereRequest);

    /**
     * Executes a request and blocks the caller thread until the response is available or a request specific timeout occurs.
     *
     * @param sphereRequest request to commercetools to perfom
     * @param timeout the maximum time to wait for this single request
     * @param unit the time unit of the timeout argument
     * @param <T> type of the result for the request
     * @return result for the request to commercetools
     * @throws SphereTimeoutException if a timeout occurs
     */
    <T> T executeBlocking(final SphereRequest<T> sphereRequest, final long timeout, final TimeUnit unit);

    /**
     * Creates a blocking client with a configured default timeout for blocking requests.
     * The asynchronous calls won't have a timeout by default.
     * @param delegate underlying sphere client which may be initialized with the {@link SphereClientFactory}.
     * @param defaultTimeout the default maximum time to wait (to block the thread) which should be greater than the timeout of the underlying HTTP client
     * @param unit the time unit of the defaultTimeout argument
     * @return wrapped client which can perform blocking calls.
     */
    static BlockingSphereClient of(final SphereClient delegate, final long defaultTimeout, final TimeUnit unit) {
        return new BlockingSphereClientImpl(delegate, defaultTimeout, unit);
    }
}
