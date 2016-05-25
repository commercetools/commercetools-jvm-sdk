package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Function;

final class RetryOperationContextImpl<P, R> extends Base implements RetryOperationContext<P> {
    final AttemptErrorResult<P> first;
    final AttemptErrorResult<P> latest;
    final Long attemptCount;
    final CompletableFuture<R> result;
    final Function<P, CompletionStage<R>> f;
    private final AutoCloseable closeable;
    private final BiConsumer<Runnable, Duration> scheduler;

    RetryOperationContextImpl(final Long attemptCount, final AttemptErrorResult<P> first, final AttemptErrorResult<P> latest, final CompletableFuture<R> result, final Function<P, CompletionStage<R>> f, final AutoCloseable closeable, final BiConsumer<Runnable, Duration> scheduler) {
        this.first = first;
        this.latest = latest;
        this.attemptCount = attemptCount;
        this.result = result;
        this.f = f;
        this.closeable = closeable;
        this.scheduler = scheduler;
    }

    @Override
    public Long getAttempt() {
        return attemptCount;
    }

    @Nonnull
    @Override
    public AttemptErrorResult<P> getFirst() {
        return first;
    }

    @Nonnull
    @Override
    public AttemptErrorResult<P> getLatest() {
        return latest;
    }

    public Function<P, CompletionStage<R>> getFunction() {
        return f;
    }

    public CompletableFuture<R> getResult() {
        return result;
    }

    public AutoCloseable getService() {
        return closeable;
    }

    public void schedule(final Runnable runnable, final Duration durationToWaitBeforeStarting) {
        scheduler.accept(runnable, durationToWaitBeforeStarting);
    }
}
