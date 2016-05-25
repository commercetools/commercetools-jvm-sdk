package io.sphere.sdk.retry;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RetryAction {
    @Nullable
    <P> RetryBehaviour<P> selectBehaviour(final RetryOperationContext<P> retryOperationContext);
}
