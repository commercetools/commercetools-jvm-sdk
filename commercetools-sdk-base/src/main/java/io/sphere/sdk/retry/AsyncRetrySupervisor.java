package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface AsyncRetrySupervisor {
    <P, R> CompletionStage<R> supervise(final Function<P, CompletionStage<R>> f, @Nullable P parameterObject);

    static AsyncRetrySupervisor of(final AutoCloseable service, final RetryRules retryRules) {
        return new AsyncRetrySupervisorImpl(service, retryRules);
    }
}
