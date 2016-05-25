package io.sphere.sdk.retry;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RetryAction {
    @Nullable
    <P> RetryResult<P> apply(final RetryOperationContext<P> retryOperationContext);
}
