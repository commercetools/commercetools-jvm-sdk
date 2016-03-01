package io.sphere.sdk.client.metrics;

import io.sphere.sdk.client.SphereRequest;

import javax.annotation.Nullable;

/**
 * The total amount of one execution of {@link SimpleMetricsSphereClient#execute(SphereRequest)} until the {@link java.util.concurrent.CompletionStage} is completed.
 * <p>Due to multithreading issues (counter start and end is in different Threads) be aware that is measurement is not perfectly accurate.</p>
 */
public final class ObservedTotalDuration extends ObservedDurationBase {
    @Nullable
    private final String correlationId;
    @Nullable
    private final Object successResult;
    @Nullable
    private final Throwable errorResult;

    private ObservedTotalDuration(final String sphereRequestId, final SphereRequest<?> request, final long durationInMilliseconds, @Nullable final String correlationId, final Object successResult, final Throwable errorResult) {
        super(sphereRequestId, request, durationInMilliseconds);
        this.correlationId = correlationId;
        this.successResult = successResult;
        this.errorResult = errorResult;
    }

    public static ObservedTotalDuration of(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request, @Nullable final String correlationId) {
        return of(durationInMilliseconds, sphereRequestId, request, correlationId, null, null);
    }

    public static ObservedTotalDuration of(final long durationInMilliseconds, final String sphereRequestId, final SphereRequest<?> request, @Nullable final String correlationId, @Nullable final Object successResult, @Nullable final Throwable errorResult) {
        return new ObservedTotalDuration(sphereRequestId, request, durationInMilliseconds, correlationId, successResult, errorResult);
    }

    @Nullable
    public Throwable getErrorResult() {
        return errorResult;
    }

    @Nullable
    public Object getSuccessResult() {
        return successResult;
    }

    @Nullable
    public String getCorrelationId() {
        return correlationId;
    }
}
