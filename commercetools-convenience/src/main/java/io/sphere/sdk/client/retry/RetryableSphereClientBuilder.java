package io.sphere.sdk.client.retry;

import io.sphere.sdk.client.QueueSphereClientDecorator;
import io.sphere.sdk.client.RetrySphereClientDecorator;
import io.sphere.sdk.client.SphereAccessTokenSupplier;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.retry.RetryAction;
import io.sphere.sdk.retry.RetryPredicate;
import io.sphere.sdk.retry.RetryRule;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * To create a Sphere Client with retry logic which computes a exponential backoff time delay in milliseconds.
 * And handle all the configurations for the creation of client.
 */
public final class RetryableSphereClientBuilder extends Base {
    protected static final long DEFAULT_MAX_DELAY = 60000;
    protected static final long DEFAULT_INITIAL_RETRY_DELAY = 200;
    protected static final int DEFAULT_MAX_RETRY_ATTEMPT = 5;
    protected static final int DEFAULT_MAX_PARALLEL_REQUESTS = 20;
    private static final List<Integer> DEFAULT_STATUS_CODES_TO_RETRY = Arrays.asList(500, 502, 503, 504);

    private final SphereClientConfig sphereClientConfig;
    private final HttpClient httpClient;
    private long maxDelay;
    private long initialRetryDelay;
    private int maxRetryAttempt;
    private int maxParallelRequests;
    private List<Integer> statusCodesToRetry;

    private RetryableSphereClientBuilder(
            @Nonnull final SphereClientConfig sphereClientConfig,
            @Nonnull final HttpClient httpClient) {

        this.sphereClientConfig = sphereClientConfig;
        this.httpClient = httpClient;
        this.maxDelay = DEFAULT_MAX_DELAY;
        this.initialRetryDelay = DEFAULT_INITIAL_RETRY_DELAY;
        this.maxRetryAttempt = DEFAULT_MAX_RETRY_ATTEMPT;
        this.maxParallelRequests = DEFAULT_MAX_PARALLEL_REQUESTS;
        this.statusCodesToRetry = DEFAULT_STATUS_CODES_TO_RETRY;
    }

    /**
     * Creates a new instance of {@link RetryableSphereClientBuilder} given a {@link SphereClientConfig}
     * responsible for creation of a SphereClient.
     *
     * @param sphereClientConfig the client configuration for the client.
     * @param httpClient         client to execute requests
     * @return the instantiated {@link RetryableSphereClientBuilder}.
     */
    public static RetryableSphereClientBuilder of(
            @Nonnull final SphereClientConfig sphereClientConfig,
            @Nonnull final HttpClient httpClient) {

        return new RetryableSphereClientBuilder(sphereClientConfig, httpClient);
    }

    /**
     * Sets the maxDelay value value in milliseconds.
     *
     * @param maxDelay - build with maxDelay value.
     * @return {@link RetryableSphereClientBuilder} with given maxDelay value.
     */
    public RetryableSphereClientBuilder withMaxDelay(final long maxDelay) {
        this.maxDelay = maxDelay;
        return this;
    }

    /**
     * Sets the initialDelay value in milliseconds.
     *
     * @param initialDelay - build with initialDelay value.
     *                     If initialDelay is equal or greater than maxDelay then, a {@link IllegalArgumentException} will be thrown.
     * @return {@link RetryableSphereClientBuilder} with given initialDelay value.
     */
    public RetryableSphereClientBuilder withInitialDelay(final long initialDelay) {
        if (initialDelay < maxDelay) {
            this.initialRetryDelay = initialDelay;
        } else {
            throw new IllegalArgumentException(
                    format("InitialDelay %s is less than MaxDelay %s.", initialDelay, maxDelay));
        }
        return this;
    }

    /**
     * Sets the Max Retry value, It should be greater than 1 for the Retry attempt.
     *
     * @param maxRetryAttempt - build with maxRetries value.
     *                        If maxRetryAttempt is less than 1 then, a {@link IllegalArgumentException} will be thrown.
     * @return {@link RetryableSphereClientBuilder} with given maxRetries value.
     */
    public RetryableSphereClientBuilder withMaxRetryAttempt(final int maxRetryAttempt) {
        if (maxRetryAttempt > 0) {
            this.maxRetryAttempt = maxRetryAttempt;
        } else {
            throw new IllegalArgumentException(format("MaxRetryAttempt %s cannot be less than 1.", maxRetryAttempt));
        }
        return this;
    }

    /**
     * Sets the Max Parallel Requests value, It should be always positive number.
     *
     * @param maxParallelRequests - build with maxParallelRequests value.
     *                            If maxParallelRequests is less than 1 then, a {@link IllegalArgumentException} will be thrown.
     * @return {@link RetryableSphereClientBuilder} with given maxParallelRequests value.
     */
    public RetryableSphereClientBuilder withMaxParallelRequests(final int maxParallelRequests) {
        if (maxParallelRequests > 0) {
            this.maxParallelRequests = maxParallelRequests;
        } else {
            throw new IllegalArgumentException(
                    format("MaxParallelRequests %s cannot be less than 0", maxParallelRequests));
        }
        return this;
    }

    /**
     * Sets the Retry Error Status Codes.
     *
     * @param statusCodesToRetry - build with retryErrorStatusCodes.
     * @return {@link RetryableSphereClientBuilder} with given retryErrorStatusCodes.
     */
    public RetryableSphereClientBuilder withStatusCodesToRetry(final List<Integer> statusCodesToRetry) {
        this.statusCodesToRetry = statusCodesToRetry;
        return this;
    }

    /**
     * creates a SphereClient using the class configuration values.
     *
     * @return the instantiated {@link SphereClient}
     */
    public SphereClient build() {
        final SphereClient underlyingClient = createUnderlyingSphereClient(httpClient, sphereClientConfig);
        return decorateSphereClient(underlyingClient, RetryAction.ofExponentialBackoff(maxRetryAttempt, initialRetryDelay, maxDelay), maxParallelRequests);
    }

    private SphereClient createUnderlyingSphereClient(
            @Nonnull final HttpClient httpClient,
            @Nonnull final SphereClientConfig clientConfig) {
        final SphereAccessTokenSupplier tokenSupplier =
                SphereAccessTokenSupplier.ofAutoRefresh(clientConfig, httpClient, false);
        return SphereClient.of(clientConfig, httpClient, tokenSupplier);
    }

    protected SphereClient decorateSphereClient(
            @Nonnull final SphereClient underlyingClient,
            @Nonnull final RetryAction retryAction,
            final int maxParallelRequests) {
        final SphereClient retryClient = withRetry(underlyingClient, retryAction);
        return withLimitedParallelRequests(retryClient, maxParallelRequests);
    }

    private SphereClient withRetry(@Nonnull final SphereClient delegate, final RetryAction retryAction) {
        final RetryPredicate http5xxMatcher = RetryPredicate.ofMatchingStatusCodes(
                errCode -> statusCodesToRetry.stream().anyMatch(i -> i.equals(errCode)));
        final List<RetryRule> retryRules = Collections.singletonList(RetryRule.of(http5xxMatcher, retryAction));
        return RetrySphereClientDecorator.of(delegate, retryRules);
    }

    private SphereClient withLimitedParallelRequests(final SphereClient delegate, final int maxParallelRequests) {
        return QueueSphereClientDecorator.of(delegate, maxParallelRequests);
    }

}
