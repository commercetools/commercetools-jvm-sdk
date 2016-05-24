package io.sphere.sdk.retry;

import javax.annotation.Nonnull;

public interface RetryContext<P> {
    @Nonnull
    AttemptErrorResult<P> getFirst();

    @Nonnull
    AttemptErrorResult<P> getLatest();

    Long getAttemptCount();
}
