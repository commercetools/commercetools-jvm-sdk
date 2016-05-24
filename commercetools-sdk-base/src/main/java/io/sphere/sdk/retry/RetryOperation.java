package io.sphere.sdk.retry;

import javax.annotation.Nullable;

@FunctionalInterface
public interface RetryOperation {
    //TDOO document requires side effects
    @Nullable
    <P, R> RetryOutput<P, R> handle(final RetryOperationContext<P, R> retryOperationContext);
}
