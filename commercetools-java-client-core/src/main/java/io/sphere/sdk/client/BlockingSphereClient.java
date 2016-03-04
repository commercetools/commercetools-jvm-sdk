package io.sphere.sdk.client;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * A client for commercetools which provides thread blocking and non-blocking methods to execute a request.
 *
 * The implementations should be thread-safe so they can be used by multiple threads in parallel.
 *
 *  <p>Creation requires a {@link SphereClient} and you specify the general timeout for all requests:</p>
 {@include.example io.sphere.sdk.client.BlockingClientCreationDemo}

 <p>Get a value blocking with {@link #executeBlocking(SphereRequest)}:</p>
 {@include.example io.sphere.sdk.meta.BlockingClientValueGetDemo}
 <p>Get a value without blocking like in {@link SphereClient#execute(SphereRequest)}:</p>
 {@include.example io.sphere.sdk.meta.BlockingClientValueAsyncGetDemo}
 <p>{@link SphereTimeoutException} occurs if the answer is not available in the configured timeout:</p>
 {@include.example io.sphere.sdk.client.BlockingSphereClientTest#globalTimeout()}
 <p>In case of errors sphere exceptions are directly thrown:</p>
 {@include.example io.sphere.sdk.client.BlockingClientSphereExceptionDemo}
 *
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
     * Executes a request and blocks the caller thread until the response is available or a request specific timeout occurs.
     *
     * @param sphereRequest request to commercetools to perfom
     * @param duration the maximum duration to wait for this single request
     * @param <T> type of the result for the request
     * @return result for the request to commercetools
     * @throws SphereTimeoutException if a timeout occurs
     */
    <T> T executeBlocking(final SphereRequest<T> sphereRequest, final Duration duration);

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

    /**
     * Creates a blocking client with a configured default timeout for blocking requests.
     * The asynchronous calls won't have a timeout by default.
     * @param delegate underlying sphere client which may be initialized with the {@link SphereClientFactory}.
     * @param defaultTimeout the default maximum duration to wait (to block the thread) which should be greater than the timeout of the underlying HTTP client
     * @return wrapped client which can perform blocking calls.
     */
    static BlockingSphereClient of(final SphereClient delegate, final Duration defaultTimeout) {
        return of(delegate, defaultTimeout.toMillis(), TimeUnit.MILLISECONDS);
    }
}
