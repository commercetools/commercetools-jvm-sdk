package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface AsyncRetrySupervisor extends AutoCloseable {
    <P, R> CompletionStage<R> supervise(final AutoCloseable service, final Function<P, CompletionStage<R>> f, @Nullable P parameterObject);

    @Override
    void close() throws Exception;

    static AsyncRetrySupervisor of(final List<RetryRule> retryRules) {
        return new AsyncRetrySupervisorImpl(retryRules);
    }
}
