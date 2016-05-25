package io.sphere.sdk.retry;

import java.time.Duration;
import java.util.function.Function;

public final class RetryOperations {

    private RetryOperations() {
    }

    private static RetryContext convert(final RetryOperationContext<?> c) {
        return new RetryContextImpl<>(c.getAttempt(), c.getLatest().getError(), c.getLatest().getParameter());
    }


    //TODO param??? maybe RetryRuleContext? rename parameter?
    public static RetryAction scheduledRetry(final long maxAttempts, final Function<RetryContext, Duration> f) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryBehaviour.resume(retryOperationContext.getLatest().getError());
                } else {
                    final Duration duration = f.apply(convert(retryOperationContext));
                    final P parameterObject = retryOperationContext.getLatest().getParameter();
                    return RetryBehaviour.retryScheduled(parameterObject, duration);
                }
            }

            @Override
            protected String getDescription() {
                return "schedule retry retry up to " + maxAttempts + " times";
            }
        };
    }

    private static void validateMaxAttempts(final long maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Max attempts must be greater than 0.");
        }
    }

    public static RetryAction shutdownServiceAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext) {
                return RetryBehaviour.stop(retryOperationContext.getFirst().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    public static RetryAction shutdownServiceAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext) {
                return RetryBehaviour.stop(retryOperationContext.getLatest().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    public static RetryAction giveUpAndSendFirstException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext) {
                return RetryBehaviour.resume(retryOperationContext.getFirst().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    public static RetryAction giveUpAndSendLatestException() {
        return new RetryActionImpl() {
            @Override
            public <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext) {
                return RetryBehaviour.resume(retryOperationContext.getLatest().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    public static RetryAction immediateRetries(final long maxAttempts) {
        validateMaxAttempts(maxAttempts);

        return new RetryActionImpl() {
            @Override
            public <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return RetryBehaviour.resume(retryOperationContext.getLatest().getError());
                } else {
                    final P parameterObject = retryOperationContext.getLatest().getParameter();
                    return RetryBehaviour.retryImmediately(parameterObject);
                }
            }

            @Override
            protected String getDescription() {
                return "immediate retry up to " + maxAttempts + " times";
            }
        };
    }
}
