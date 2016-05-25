package io.sphere.sdk.retry;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface RetryOperationContext<P, R> extends RetryContext {
    @Override
    Long getAttemptCount();

    @Nonnull
    @Override
    AttemptErrorResult<P> getFirst();

    @Nonnull
    @Override
    AttemptErrorResult<P> getLatest();

    Function<P, CompletionStage<R>> getFunction();

    CompletableFuture<R> getResult();

    AutoCloseable getService();

    void schedule(final Runnable runnable, final Duration durationToWaitBeforeStarting);
}
