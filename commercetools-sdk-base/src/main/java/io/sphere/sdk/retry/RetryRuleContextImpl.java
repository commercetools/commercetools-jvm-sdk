package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

final class RetryRuleContextImpl<P> extends Base implements RetryRuleContext {
    private final Throwable error;
    private final Object parameterObject;
    private final Long attemptCount;

    public RetryRuleContextImpl(final Long attemptCount, final Throwable error, final Object parameterObject) {
        this.attemptCount = attemptCount;
        this.error = error;
        this.parameterObject = parameterObject;
    }

    @Override
    public Long getAttempt() {
        return attemptCount;
    }

    public Throwable getError() {
        return error;
    }

    @Nullable
    @Override
    public Object getParameter() {
        return parameterObject;
    }
}
