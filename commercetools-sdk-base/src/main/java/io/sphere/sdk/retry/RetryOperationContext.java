package io.sphere.sdk.retry;

import javax.annotation.Nonnull;

public interface RetryOperationContext<P> {
    Long getAttempt();

    @Nonnull
    AttemptErrorResult<P> getFirst();

    @Nonnull
    AttemptErrorResult<P> getLatest();
}
