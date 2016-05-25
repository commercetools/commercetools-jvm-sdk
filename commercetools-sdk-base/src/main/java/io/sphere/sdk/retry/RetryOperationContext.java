package io.sphere.sdk.retry;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface RetryOperationContext<P, R> {
    Long getAttempt();

    @Nonnull
    AttemptErrorResult<P> getFirst();

    @Nonnull
    AttemptErrorResult<P> getLatest();

    Function<P, CompletionStage<R>> getFunction();

    CompletableFuture<R> getResult();

    AutoCloseable getService();

    void schedule(final Runnable runnable, final Duration durationToWaitBeforeStarting);
}
