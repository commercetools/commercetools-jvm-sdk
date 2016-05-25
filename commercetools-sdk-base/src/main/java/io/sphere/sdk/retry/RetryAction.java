package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.function.Function;

import static io.sphere.sdk.retry.RetryActions.convert;
import static io.sphere.sdk.retry.RetryActions.validateMaxAttempts;

@FunctionalInterface
public interface RetryAction {
    @Nullable
    <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext);

    //TODO param??? maybe RetryRuleContext? rename parameter?
    static RetryAction scheduledRetry(final long maxAttempts, final Function<RetryContext, Duration> f) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryResult.resume(retryOperationContext.getLatest().getError());
                } else {
                    final Duration duration = f.apply(convert(retryOperationContext));
                    final P parameterObject = retryOperationContext.getLatest().getParameter();
                    return RetryResult.retryScheduled(parameterObject, duration);
                }
            }

            @Override
            protected String getDescription() {
                return "schedule retry retry up to " + maxAttempts + " times";
            }
        };
    }

    static RetryAction shutdownServiceAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext) {
                return RetryResult.stop(retryOperationContext.getFirst().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    static RetryAction shutdownServiceAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext) {
                return RetryResult.stop(retryOperationContext.getLatest().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    static RetryAction giveUpAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext) {
                return RetryResult.resume(retryOperationContext.getFirst().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    static RetryAction giveUpAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext) {
                return RetryResult.resume(retryOperationContext.getLatest().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    static RetryAction immediateRetries(final long maxAttempts) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryResult.resume(retryOperationContext.getLatest().getError());
                } else {
                    final P parameterObject = retryOperationContext.getLatest().getParameter();
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
