package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

final class AsyncRetrySupervisorImpl extends Base implements AsyncRetrySupervisor {
    private static final Logger logger = LoggerFactory.getLogger(RetryOperation.class);
    private final List<RetryRule> retryRules;
    private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(0);

    AsyncRetrySupervisorImpl(final List<RetryRule> retryRules) {
        this.retryRules = retryRules;
    }

    @Override
    public <P, R> CompletionStage<R> supervise(final AutoCloseable service,
                                               final Function<P, CompletionStage<R>> f,
                                               @Nullable final P parameterObject) {
        final CompletionStage<R> initialCompletionStage = f.apply(parameterObject);
        final CompletableFuture<R> result = new CompletableFuture<>();
        initialCompletionStage.whenComplete((res, firstError) -> {
            final boolean isErrorCase = firstError != null;
            if (isErrorCase) {
                final RetryOperationContextImpl<P, R> retryOperationContext = createFirstRetryOperationContext(firstError, result, f, parameterObject, service);
                handle(retryOperationContext);
            } else {
                result.complete(res);
            }
        });
        return result;
    }

    @Override
    public void close() throws Exception {
        executor.shutdownNow();
    }

    private <P, R> RetryOperationContextImpl<P, R> createFirstRetryOperationContext(final Throwable throwable, final CompletableFuture<R> result, final Function<P, CompletionStage<R>> f, final P parameterObject, final AutoCloseable service) {
        final long attemptCount = 1L;
        final Instant now = Instant.now();
        final AttemptErrorResult<P> firstAttemptErrorResult = new AttemptErrorResultImpl<>(throwable, now, parameterObject);
        final AttemptErrorResult<P> latestErrorResult = firstAttemptErrorResult;
        return new RetryOperationContextImpl<>(attemptCount, firstAttemptErrorResult, latestErrorResult, result, f, service, this::schedule);
    }

    private void schedule(final Runnable r, final Duration d) {
        executor.schedule(r, d.toMillis(), TimeUnit.MILLISECONDS);
    }

    private <P, R> void handle(final RetryOperationContextImpl<P, R> retryOperationContext) {
        final RetryOperation retryOperation = getRetryOperation(new RetryContextImpl<>(retryOperationContext.getAttempt(), retryOperationContext.getLatest().getError(), retryOperationContext.getLatest().getParameter()));
        final RetryBehaviour<P> retryBehaviour = retryOperation.selectBehaviour(retryOperationContext);
        if (retryBehaviour.getStrategy() == RetryBehaviour.Strategy.RESUME) {
            retryOperationContext.getResult().completeExceptionally(retryBehaviour.getError());
        } else if (retryBehaviour.getStrategy() == RetryBehaviour.Strategy.STOP) {
            try {
                retryOperationContext.getService().close();
            } catch (final Exception e) {
                logger.error("Error occurred while closing service in retry strategy.", e);
            }
            retryOperationContext.getResult().completeExceptionally(retryBehaviour.getError());
        } else if (retryBehaviour.getStrategy() == RetryBehaviour.Strategy.RETRY_IMMEDIATELY) {
            final P parameter = retryBehaviour.getParameter();
            final CompletionStage<R> completionStage = retryOperationContext.getFunction().apply(parameter);
            handleResultAndEnqueueErrorHandlingAgain(completionStage, parameter, retryOperationContext);
        } else if (retryBehaviour.getStrategy() == RetryBehaviour.Strategy.RETRY_SCHEDULED) {
            final Duration duration = retryBehaviour.getDuration();
            final P parameter = retryOperationContext.getLatest().getParameter();
            retryOperationContext.schedule(() -> {
                final CompletionStage<R> completionStage = retryOperationContext.getFunction().apply(parameter);
                handleResultAndEnqueueErrorHandlingAgain(completionStage, parameter, retryOperationContext);
            }, duration);
        } else {
            throw new IllegalStateException("illegal state for " + retryBehaviour);
        }
    }

    private <P, R> void handleResultAndEnqueueErrorHandlingAgain(final CompletionStage<R> completionStage, final P parameter, final RetryOperationContextImpl<P, R> retryOperationContext) {
        //todo use own thread pool
        completionStage.whenCompleteAsync((res, error) -> {
            final boolean isErrorCase = error != null;
            if (isErrorCase) {
                final AttemptErrorResult<P> attemptErrorResult = new AttemptErrorResultImpl<>(error, Instant.now(), parameter);
                final RetryOperationContextImpl<P, R> nextContext = getNextContext(attemptErrorResult, retryOperationContext);
                handle(nextContext);
            } else {
                retryOperationContext.getResult().complete(res);
            }
        });
    }

    private <P> RetryOperation getRetryOperation(final RetryContext retryContext) {
        return retryRules.stream()
                .filter(rule -> rule.test(retryContext))
                .findFirst()
                .map(rule -> rule.selectRetryOperation(retryContext))
                .orElseGet(() -> RetryOperations.giveUpAndSendLatestException());
    }

    private <P, R> RetryOperationContextImpl<P, R> getNextContext(final AttemptErrorResult<P> attemptErrorResult, final RetryOperationContextImpl<P, R> parentContext) {
        final long attemptCount = parentContext.getAttempt() + 1;
        final AttemptErrorResult<P> firstAttemptErrorResult = parentContext.getFirst();
        return new RetryOperationContextImpl<>(attemptCount, firstAttemptErrorResult, attemptErrorResult, parentContext.getResult(), parentContext.getFunction(), parentContext.getService(), this::schedule);
    }
}
