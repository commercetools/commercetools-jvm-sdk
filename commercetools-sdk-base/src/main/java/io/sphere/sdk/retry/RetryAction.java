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
                    try {
                        final Duration duration = durationFunction.apply(retryOperationContext);
                        final Object parameterObject = retryOperationContext.getLatestParameter();
                        return RetryStrategy.retryScheduled(parameterObject, duration);
                    } catch (Throwable e) {
                        return RetryStrategy.resume(e);
                    }
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

    /**
     * Retry in the future with exponential backoff strategy.
     * @param maxAttempts maximum amount of attempts until giving up and throwing the latest Exception
     * @param initialRetryDelay initial time to wait before retrying in milliseconds
     * @param maxDelay maximum time to wait before retrying in milliseconds
     * @return action
     */
    static RetryAction ofExponentialBackoff(final long maxAttempts, final long initialRetryDelay, final long maxDelay) {
        return ofScheduledRetry(maxAttempts, c -> calculateDurationWithExponentialRandomBackoff(c.getAttempt(), initialRetryDelay, maxDelay));
    }

    /**
     * Computes a exponential backoff time delay in milliseconds to be used in retries, the delay grows with failed
     * retry attempts count with a randomness interval.
     * (see: <a href="https://aws.amazon.com/blogs/architecture/exponential-backoff-and-jitter"/>)
     * (see: <a href="http://dthain.blogspot.com/2009/02/exponential-backoff-in-distributed.html"/>)
     *
     * @param retryAttempt the number of attempts already tried by the client.
     * @param initialRetryDelay the initial Retry delay.
     * @param maxDelay the maxDelay in milliseconds.
     * @return a duration in milliseconds, that grows with the number of failed attempts.
     */
    static Duration calculateDurationWithExponentialRandomBackoff(final long retryAttempt,
                                                                  final long initialRetryDelay,
                                                                  final long maxDelay) {
        final double exponentialFactor = Math.pow(2, retryAttempt - 1);
        final double jitter = 1 + Math.random();
        final long delay = (long)Math.min(initialRetryDelay * exponentialFactor * jitter, maxDelay);
        return Duration.ofMillis(delay);
    }
}
