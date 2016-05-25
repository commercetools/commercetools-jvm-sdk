package io.sphere.sdk.retry;

import io.sphere.sdk.utils.CompletableFutureUtils;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public final class RetryOperations {

    private RetryOperations() {
    }

    public static RetryOperation scheduledRetry(final long maxAttempts, final Function<RetryOperationContext, Duration> f) {
        validateMaxAttempts(maxAttempts);

        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return giveUpUsingThrowable(retryOperationContext, retryOperationContext.getLatest().getError());
                } else {
                    final Duration duration = f.apply(retryOperationContext);
                    final CompletableFuture<R> future = new CompletableFuture<>();
                    final P parameterObject = retryOperationContext.getLatest().getParameter();
                    retryOperationContext.schedule(() -> {
                        final CompletionStage<R> completionStage = retryOperationContext.getFunction().apply(parameterObject);
                        CompletableFutureUtils.transferResult(completionStage, future);
                    }, duration);
                    return new RetryOutputImpl<>(future, parameterObject);
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

    public static RetryOperation shutdownServiceAndSendFirstException() {
        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                shutdownAndThrow(retryOperationContext, retryOperationContext.getFirst().getError());
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
                shutdownAndThrow(retryOperationContext, retryOperationContext.getLatest().getError());
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
                return giveUpUsingThrowable(retryOperationContext, retryOperationContext.getFirst().getError());
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
                return giveUpUsingThrowable(retryOperationContext, retryOperationContext.getLatest().getError());
            }

            @Override
            protected String getDescription() {
                return "shut down and send latest exception";
            }
        };
    }

    public static RetryOperation immediateRetries(final long maxAttempts) {
        validateMaxAttempts(maxAttempts);

        return new RetryOperationImpl() {
            @Override
            public <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext) {
                if (retryOperationContext.getAttempt() > maxAttempts) {
                    return giveUpUsingThrowable(retryOperationContext, retryOperationContext.getLatest().getError());
                } else {
                    final P parameterObject = retryOperationContext.getLatest().getParameter();
                    final CompletionStage<R> completionStage = retryOperationContext.getFunction().apply(parameterObject);
                    return new RetryOutputImpl<>(completionStage, parameterObject);
                }
            }

            @Override
            protected String getDescription() {
                return "immediate retry up to " + maxAttempts + " times";
            }
        };
    }
}
