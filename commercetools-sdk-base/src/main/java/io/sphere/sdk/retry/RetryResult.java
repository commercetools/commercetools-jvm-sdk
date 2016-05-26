package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Duration;

public final class RetryResult {
    enum Strategy {
        RESUME, STOP, RETRY_IMMEDIATELY, RETRY_SCHEDULED;
    }

    private final Strategy strategy;
    @Nullable
    private final Throwable error;
    @Nullable
    private final Object parameter;
    @Nullable
    private final Duration duration;

    private RetryResult(final Strategy strategy, final Throwable error, final Object parameter, final Duration duration) {
        this.strategy = strategy;
        this.duration = duration;
        this.error = error;
        this.parameter = parameter;
    }

    public static RetryResult resume(final Throwable error) {
        return new RetryResult(Strategy.RESUME, error, null, null);
    }

    public static RetryResult stop(final Throwable error) {
        return new RetryResult(Strategy.STOP, error, null, null);
    }

    public static RetryResult retryImmediately(final Object parameter) {
        return new RetryResult(Strategy.RETRY_IMMEDIATELY, null, parameter, null);
    }

    public static RetryResult retryScheduled(final Object parameter, final Duration duration) {
        return new RetryResult(Strategy.RETRY_SCHEDULED, null, parameter, duration);
    }

    @Nullable
    Duration getDuration() {
        return duration;
    }

    @Nullable
    Throwable getError() {
        return error;
    }

    @Nullable
    Object getParameter() {
        return parameter;
    }

    Strategy getStrategy() {
        return strategy;
    }
}
