package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.function.Function;

import static io.sphere.sdk.retry.RetryActions.validateMaxAttempts;

/**
 * Provides some default implementations for {@link RetryRule#apply(RetryContext)}.
 */
@FunctionalInterface
public interface RetryAction extends Function<RetryContext, RetryStrategy> {
    @Nullable
    RetryStrategy apply(final RetryContext retryContext);

    /**
     * Retry in the future with a fixed waiting time.
     *
     * @param maxAttempts maximum amount of attempts until giving up and throwing the latest Exception
     * @param duration time to wait before retrying
     * @return action
     */
    static RetryAction ofScheduledRetry(final long maxAttempts, final Duration duration) {
        return ofScheduledRetry(maxAttempts, c -> duration);
    }

    /**
     * Retry in the future with a waiting time depending on the {@link RetryContext}, for example to take the number of
     * attempts in consideration.
     *
     * {@include.example io.sphere.sdk.client.retry.RetryBadGatewayExample}
     *
     * @param maxAttempts maximum amount of attempts until giving up and throwing the latest Exception
     * @param durationFunction function that takes the {@link RetryContext} and provides the duration to wait until the next retry
     * @return action
     */
    static RetryAction ofScheduledRetry(final long maxAttempts, final Function<RetryContext, Duration> durationFunction) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public RetryStrategy apply(final RetryContext retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryStrategy.resume(retryOperationContext.getLatestError());
                } else {
                    final Duration duration = durationFunction.apply(retryOperationContext);
                    final Object parameterObject = retryOperationContext.getLatestParameter();
                    return RetryStrategy.retryScheduled(parameterObject, duration);
                }
            }

            @Override
            protected String getDescription() {
                return "schedule retry up to " + maxAttempts + " times";
            }
        };
    }

    static RetryAction ofShutdownServiceAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public RetryStrategy apply(final RetryContext retryOperationContext) {
                return RetryStrategy.stop(retryOperationContext.getFirstError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    static RetryAction ofShutdownServiceAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public RetryStrategy apply(final RetryContext retryOperationContext) {
                return RetryStrategy.stop(retryOperationContext.getLatestError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    static RetryAction ofGiveUpAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public RetryStrategy apply(final RetryContext retryOperationContext) {
                return RetryStrategy.resume(retryOperationContext.getFirstError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    /**
     * Throws the latest error and does not retry.
     *
     * @return action
     */
    static RetryAction ofGiveUpAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public RetryStrategy apply(final RetryContext retryOperationContext) {
                return RetryStrategy.resume(retryOperationContext.getLatestError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    /**
     * Retries immediately for a maximum amount of attempts.
     *
     * @param maxAttempts maximum amount of attempts until giving up and throwing the latest Exception
     * @return action
     */
    static RetryAction ofImmediateRetries(final long maxAttempts) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public RetryStrategy apply(final RetryContext retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryStrategy.resume(retryOperationContext.getLatestError());
                } else {
                    final Object parameterObject = retryOperationContext.getLatestParameter();
                    return RetryStrategy.retryImmediately(parameterObject);
                }
            }

            @Override
            protected String getDescription() {
                return "immediate retry up to " + maxAttempts + " times";
            }
        };
    }
}
