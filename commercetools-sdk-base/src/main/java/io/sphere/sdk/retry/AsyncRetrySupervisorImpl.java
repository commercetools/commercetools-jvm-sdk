package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;

final class AsyncRetrySupervisorImpl extends Base implements AsyncRetrySupervisor {
    private static final Logger logger = LoggerFactory.getLogger(AsyncRetrySupervisor.class);
    private final List<RetryRule> retryRules;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    AsyncRetrySupervisorImpl(final List<RetryRule> retryRules) {
        this.retryRules = retryRules;
    }

    @Override
    public <P, R> CompletionStage<R> supervise(final AutoCloseable service,
                                               final Function<P, CompletionStage<R>> f,
                                               @Nullable final P parameterObject) {
        final CompletableFuture<R> result = new CompletableFuture<>();
        try {
            final CompletionStage<R> initialCompletionStage = f.apply(parameterObject);
            initialCompletionStage.whenCompleteAsync((res, firstError) -> {
                final boolean isErrorCase = firstError != null;
                if (isErrorCase) {
                    final RetryContextImpl<P, R> retryOperationContext = createFirstRetryOperationContext(firstError, result, f, parameterObject, service);
                    handle(retryOperationContext);
                } else {
                    result.complete(res);
                }
            }, executor);
        } catch (final Throwable e) {//necessary if f.apply() throws directly an exception
            result.completeExceptionally(e);
        }
        return result;
    }

    @Override
    public void close() {
        executor.shutdownNow();
    }

    private <P, R> RetryContextImpl<P, R> createFirstRetryOperationContext(final Throwable throwable, final CompletableFuture<R> result, final Function<P, CompletionStage<R>> f, final P parameterObject, final AutoCloseable service) {
        final long attemptCount = 1L;
        final Instant now = Instant.now();
        return new RetryContextImpl<>(now, attemptCount, throwable, parameterObject, throwable, parameterObject, result, f, service, this::schedule);
    }

    private void schedule(final Runnable r, final Duration d) {
        executor.schedule(r, d.toMillis(), TimeUnit.MILLISECONDS);
    }

    private <P, R> void handle(final RetryContextImpl<P, R> retryContext) {
        final RetryResult retryResult = getRetryOperation(retryContext).apply(retryContext);
        if (retryResult.getStrategy() == RetryResult.Strategy.RESUME) {
            retryContext.getResult().completeExceptionally(retryResult.getError());
        } else if (retryResult.getStrategy() == RetryResult.Strategy.STOP) {
            final CompletableFuture<R> result = retryContext.getResult();
            result.completeExceptionally(retryResult.getError());
            try {
                retryContext.getService().close();
            } catch (final Exception e) {
                logger.error("Error occurred while closing service in retry strategy.", e);
            }
        } else {
            final Function<P, CompletionStage<R>> function = retryContext.getFunction();
            if (retryResult.getStrategy() == RetryResult.Strategy.RETRY_IMMEDIATELY) {
                final Object parameter = retryResult.getParameter();
                final CompletionStage<R> completionStage = forceApply(function, parameter);
                handleResultAndEnqueueErrorHandlingAgain(completionStage, parameter, retryContext);
            } else if (retryResult.getStrategy() == RetryResult.Strategy.RETRY_SCHEDULED) {
                final Duration duration = retryResult.getDuration();
                final Object parameter = retryResult.getParameter();
                retryContext.schedule(() -> {
                    final CompletionStage<R> completionStage = forceApply(function, parameter);
                    handleResultAndEnqueueErrorHandlingAgain(completionStage, parameter, retryContext);
                }, duration);
            } else {
                throw new IllegalStateException("illegal state for " + retryResult);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <P, R> CompletionStage<R> forceApply(final Function<P, CompletionStage<R>> function, final Object parameter) {
        return function.apply((P) parameter);
    }

    private <P, R> void handleResultAndEnqueueErrorHandlingAgain(final CompletionStage<R> completionStage, final Object parameter, final RetryContextImpl<P, R> retryOperationContext) {
        completionStage.whenCompleteAsync((res, error) -> {
            final boolean isErrorCase = error != null;
            if (isErrorCase) {
                final RetryContextImpl<P, R> nextContext = retryOperationContext.withNewFailedAttempt(error, parameter);
                handle(nextContext);
            } else {
                retryOperationContext.getResult().complete(res);
            }
        }, executor);
    }

    private RetryAction getRetryOperation(final RetryContext retryContext) {
        final Optional<RetryAction> matchingRetryAction = RetryRule.findMatchingRetryAction(retryRules, retryContext);
        return matchingRetryAction
                .orElseGet(() -> RetryAction.giveUpAndSendLatestException());
    }

}
