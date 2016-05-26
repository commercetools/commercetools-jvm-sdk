package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Function;

final class RetryContextImpl<P, R> extends Base implements RetryContext {
    private final Instant startTimestamp;
    private final Throwable firstError;
    private final Throwable latestError;
    @Nullable
    private final Object firstParameter;
    @Nullable
    private final Object latestParameter;
    private final Long attempt;

    private final CompletableFuture<R> result;
    private final Function<P, CompletionStage<R>> f;
    private final AutoCloseable closeable;
    private final BiConsumer<Runnable, Duration> scheduler;

    public RetryContextImpl(final Instant startTimestamp, final Long attempt, final Throwable firstError, final Object firstParameter, final Throwable latestError, final Object latestParameter, final CompletableFuture<R> result, final Function<P, CompletionStage<R>> f, final AutoCloseable closeable, final BiConsumer<Runnable, Duration> scheduler) {
        this.attempt = attempt;
        this.startTimestamp = startTimestamp;
        this.firstError = firstError;
        this.latestError = latestError;
        this.firstParameter = firstParameter;
        this.latestParameter = latestParameter;
        this.result = result;
        this.f = f;
        this.closeable = closeable;
        this.scheduler = scheduler;
    }

    @Override
    public Long getAttempt() {
        return attempt;
    }

    @Override
    public Throwable getFirstError() {
        return firstError;
    }

    @Override
    @Nullable
    public Object getFirstParameter() {
        return firstParameter;
    }

    @Override
    public Throwable getLatestError() {
        return latestError;
    }

    @Override
    @Nullable
    public Object getLatestParameter() {
        return latestParameter;
    }

    @Override
    public Instant getStartTimestamp() {
        return startTimestamp;
    }

    Function<P, CompletionStage<R>> getFunction() {
        return f;
    }

    CompletableFuture<R> getResult() {
        return result;
    }

    AutoCloseable getService() {
        return closeable;
    }

    void schedule(final Runnable runnable, final Duration durationToWaitBeforeStarting) {
        scheduler.accept(runnable, durationToWaitBeforeStarting);
    }
}
