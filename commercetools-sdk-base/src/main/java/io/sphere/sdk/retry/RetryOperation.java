package io.sphere.sdk.retry;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RetryOperation {
    @Nullable
    <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext);
}
