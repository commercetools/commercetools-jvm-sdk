package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.retry.RetryStrategy.StrategyType;
import io.sphere.sdk.utils.SphereInternalLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Function;

import static java.lang.String.format;

final class AsyncRetrySupervisorImpl extends Base implements AsyncRetrySupervisor {
    private static final SphereInternalLogger logger = SphereInternalLogger.getLogger(AsyncRetrySupervisor.class);
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
        final RetryStrategy retryStrategy = applyContext(retryContext);
        final StrategyType strategyType = retryStrategy.getStrategyType();
        if (strategyType == StrategyType.RESUME || strategyType == StrategyType.STOP) {
            retryContext.getResult().completeExceptionally(retryStrategy.getError());
            if (strategyType == StrategyType.STOP) {
                closeService(retryContext);
            }
        } else {
            final Function<P, CompletionStage<R>> function = retryContext.getFunction();
            final Object parameter = retryStrategy.getParameter();
            if (strategyType == StrategyType.RETRY_IMMEDIATELY) {
                retry(retryContext, function, parameter);
            } else if (strategyType == StrategyType.RETRY_SCHEDULED) {
                final Duration duration = retryStrategy.getDuration();
                retryContext.schedule(() -> retry(retryContext, function, parameter), duration);
            } else {
                throw new IllegalStateException("illegal state for " + retryStrategy);
            }
        }
    }

    private <P, R> void retry(final RetryContextImpl<P, R> retryContext, final Function<P, CompletionStage<R>> function, final Object parameter) {
        logRetry(retryContext);
        final CompletionStage<R> completionStage = forceApply(function, parameter);
        handleResultAndEnqueueErrorHandlingAgain(completionStage, parameter, retryContext);
    }

    private <P, R>  void logRetry(final RetryContextImpl<P, R> retryContext) {
        logger.info(() -> {
            final String output;
            final Throwable error = retryContext.getLatestError();
            if (error instanceof SphereException) {
                output = ((SphereException) error).httpSummary();
            } else {
                output = "";
            }
            return format("We have already retried [%d] times.\n%s", retryContext.getAttempt(), output).trim();
        });
        logger.trace(() -> format("We have already retried [%d] times.\n[%s]", retryContext.getAttempt(), retryContext.getLatestError()).trim());
    }

    private <P, R> void closeService(final RetryContextImpl<P, R> retryContext) {
        try {
            retryContext.getService().close();
        } catch (final Exception e) {
            logger.error(() -> "Error occurred while closing service in retry strategy.", e);
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

    private RetryStrategy applyContext(final RetryContext retryContext) {
        final Optional<RetryRule> matchingRetryRuleOption = findMatchingRetryRule(retryRules, retryContext);
        final RetryRule matchingRetryRule = matchingRetryRuleOption
                .orElseGet(() -> RetryRule.of(RetryPredicate.ofAlwaysTrue(), RetryAction.ofGiveUpAndSendLatestException()));
        return matchingRetryRule.apply(retryContext);
    }

    private static Optional<RetryRule> findMatchingRetryRule(final List<RetryRule> retryRules, final RetryContext retryContext) {
        return retryRules.stream()
                .filter(rule -> rule.test(retryContext))
                .findFirst();
    }
}
