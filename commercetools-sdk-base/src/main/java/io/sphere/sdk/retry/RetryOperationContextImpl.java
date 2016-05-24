package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

final class RetryOperationContextImpl<P, R> extends Base implements RetryOperationContext<P, R> {
    final AttemptErrorResult first;
    final AttemptErrorResult latest;
    final Long attemptCount;
    final CompletableFuture<R> result;
    final Function<P, CompletionStage<R>> f;
    private final AutoCloseable closeable;

    RetryOperationContextImpl(final Long attemptCount, final AttemptErrorResult first, final AttemptErrorResult latest, final CompletableFuture<R> result, final Function<P, CompletionStage<R>> f, final AutoCloseable closeable) {
        this.first = first;
        this.latest = latest;
        this.attemptCount = attemptCount;
        this.result = result;
        this.f = f;
        this.closeable = closeable;
    }

    @Override
    public Long getAttemptCount() {
        return attemptCount;
    }

    @Nonnull
    @Override
    public AttemptErrorResult getFirst() {
        return first;
    }

    @Nonnull
    @Override
    public AttemptErrorResult getLatest() {
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
}
