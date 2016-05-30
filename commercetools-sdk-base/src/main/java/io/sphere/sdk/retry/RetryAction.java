package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.function.Function;

import static io.sphere.sdk.retry.RetryActions.validateMaxAttempts;

@FunctionalInterface
public interface RetryAction {
    @Nullable
    RetryResult apply(final RetryContext retryContext);

    static RetryAction ofScheduledRetry(final long maxAttempts, final Duration duration) {
        return ofScheduledRetry(maxAttempts, c -> duration);
    }

    static RetryAction ofScheduledRetry(final long maxAttempts, final Function<RetryContext, Duration> f) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public RetryResult apply(final RetryContext retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryResult.resume(retryOperationContext.getLatestError());
                } else {
                    final Duration duration = f.apply(retryOperationContext);
                    final Object parameterObject = retryOperationContext.getLatestParameter();
                    return RetryResult.retryScheduled(parameterObject, duration);
                }
            }

            @Override
            protected String getDescription() {
                return "schedule retry retry up to " + maxAttempts + " times";
            }
        };
    }

    static RetryAction ofShutdownServiceAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public RetryResult apply(final RetryContext retryOperationContext) {
                return RetryResult.stop(retryOperationContext.getFirstError());
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
            public RetryResult apply(final RetryContext retryOperationContext) {
                return RetryResult.stop(retryOperationContext.getLatestError());
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
            public RetryResult apply(final RetryContext retryOperationContext) {
                return RetryResult.resume(retryOperationContext.getFirstError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    static RetryAction ofGiveUpAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public RetryResult apply(final RetryContext retryOperationContext) {
                return RetryResult.resume(retryOperationContext.getLatestError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    static RetryAction ofImmediateRetries(final long maxAttempts) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public RetryResult apply(final RetryContext retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryResult.resume(retryOperationContext.getLatestError());
                } else {
                    final Object parameterObject = retryOperationContext.getLatestParameter();
                    return RetryResult.retryImmediately(parameterObject);
                }
            }

            @Override
            protected String getDescription() {
                return "immediate retry up to " + maxAttempts + " times";
            }
        };
    }
}
