package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import javax.annotation.Nonnull;

final class RetryContextImpl<P> extends Base implements RetryContext<P> {
    final AttemptErrorResult<P> first;
    final AttemptErrorResult<P> latest;
    final Long attemptCount;

    RetryContextImpl(final Long attemptCount, final AttemptErrorResult<P> first, final AttemptErrorResult<P> latest) {
        this.first = first;
        this.latest = latest;
        this.attemptCount = attemptCount;
    }

    @Override
    public Long getAttemptCount() {
        return attemptCount;
    }

    @Nonnull
    @Override
    public AttemptErrorResult<P> getFirst() {
        return first;
    }

    @Nonnull
    @Override
    public AttemptErrorResult<P> getLatest() {
        return latest;
    }
}
