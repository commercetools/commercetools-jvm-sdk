package io.sphere.sdk.retry;

import java.util.concurrent.CompletionStage;

public final class RetryOperations {

    private RetryOperations() {
    }

    public static RetryOperation shutdownServiceAndSendFirstException() {
        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                shutdownAndThrow(retryOperationContext, retryOperationContext.getFirst().getThrowable());
                return null;
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    public static RetryOperation shutdownServiceAndSendLatestException() {
        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                shutdownAndThrow(retryOperationContext, retryOperationContext.getLatest().getThrowable());
                return null;
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    public static RetryOperation giveUpAndSendFirstException() {
        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                giveUpUsingThrowable(retryOperationContext, retryOperationContext.getFirst().getThrowable());
                return null;
            }

            @Override
            protected String getDescription() {
                return "shut down and send first exception";
            }
        };
    }

    public static RetryOperation giveUpAndSendLatestException() {
        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                giveUpUsingThrowable(retryOperationContext, retryOperationContext.getLatest().getThrowable());
                return null;
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    public static RetryOperation immediateRetries(final long maxAttempt) {
        if (maxAttempt < 1) {
            throw new IllegalArgumentException("Max attempts must be greater than 0.");
        }

        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                if (retryOperationContext.getAttemptCount() > maxAttempt) {
                    giveUpUsingThrowable(retryOperationContext, retryOperationContext.getLatest().getThrowable());
                    return null;
                } else {
                    final P parameterObject = retryOperationContext.getLatest().getParameterObject();
                    final CompletionStage<R> completionStage = retryOperationContext.getFunction().apply(parameterObject);
                    return new RetryOutputImpl<>(completionStage, parameterObject);
                }
            }

            @Override
            protected String getDescription() {
                return "immediate retry up to " + maxAttempt + " times";
            }
        };
    }
}
