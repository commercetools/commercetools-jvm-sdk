package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Internal API: A service that can wrap other service calls to make them retryable.
 *
 * <p>This class is internally used in RetrySphereClientDecorator:</p>
 * {@include.example io.sphere.sdk.client.retry.RetryBadGatewayExample}
 *
 */
public interface AsyncRetrySupervisor extends AutoCloseable {
    <P, R> CompletionStage<R> supervise(final AutoCloseable service, final Function<P, CompletionStage<R>> f, @Nullable P parameterObject);

    @Override
    void close();

    static AsyncRetrySupervisor of(final List<RetryRule> retryRules) {
        return new AsyncRetrySupervisorImpl(retryRules);
    }
}
