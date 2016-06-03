package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.time.Duration;

/**
 * Strategy how the error case should be handled
 * <ul>
 *     <li>resume, keep internal state and throw an exception</li>
 *     <li>retry immediately</li>
 *     <li>retry scheduled</li>
 *     <li>stop the service</li>
 * </ul>
 *
 */
public final class RetryStrategy extends Base {
    enum StrategyType {
        RESUME, STOP, RETRY_IMMEDIATELY, RETRY_SCHEDULED;
    }

    private final StrategyType strategyType;
    @Nullable
    private final Throwable error;
    @Nullable
    private final Object parameter;
    @Nullable
    private final Duration duration;

    private RetryStrategy(final StrategyType strategyType, final Throwable error, final Object parameter, final Duration duration) {
        this.strategyType = strategyType;
        this.duration = duration;
        this.error = error;
        this.parameter = parameter;
    }

    /**
     * The error case should be dealt with giving up and throwing an exception.
     *
     * @param error the exception to throw
     * @return strategy
     */
    public static RetryStrategy resume(final Throwable error) {
        return new RetryStrategy(StrategyType.RESUME, error, null, null);
    }

    /**
     * The error case should cause the service to stop and throw an exception.
     * @param error the exception to throw
     * @return strategy
     */
    public static RetryStrategy stop(final Throwable error) {
        return new RetryStrategy(StrategyType.STOP, error, null, null);
    }

    /**
     * The error case should be dealt with by retrying immediately with the given parameter.
     * @param parameter parameter or parameter object to retry with, can be a new one or the old one
     * @return strategy
     */
    public static RetryStrategy retryImmediately(final Object parameter) {
        return new RetryStrategy(StrategyType.RETRY_IMMEDIATELY, null, parameter, null);
    }

    /**
     * The error case should be dealt with by retrying later with the given parameter.
     * @param parameter parameter or parameter object to retry with, can be a new one or the old one
     * @param duration the amount of time to wait until the next try
     * @return strategy
     */
    public static RetryStrategy retryScheduled(final Object parameter, final Duration duration) {
        return new RetryStrategy(StrategyType.RETRY_SCHEDULED, null, parameter, duration);
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

    StrategyType getStrategyType() {
        return strategyType;
    }
}
