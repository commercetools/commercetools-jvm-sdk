package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface AsyncRetrySupervisor extends AutoCloseable {
    //TODO should in the supervise method be also the service to reuse it?


    <P, R> CompletionStage<R> supervise(final Function<P, CompletionStage<R>> f, @Nullable P parameterObject);

    @Override
    void close() throws Exception;

    static AsyncRetrySupervisor of(final AutoCloseable service, final List<RetryRule> retryRules) {
        return new AsyncRetrySupervisorImpl(service, retryRules);
    }
}
