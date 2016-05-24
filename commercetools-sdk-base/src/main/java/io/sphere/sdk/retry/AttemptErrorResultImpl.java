package io.sphere.sdk.retry;

import io.sphere.sdk.models.Base;

import java.time.Instant;

final class AttemptErrorResultImpl<P> extends Base implements AttemptErrorResult<P> {
    private final Throwable error;
    private final Instant timestamp;
    private final P parameterObject;

    AttemptErrorResultImpl(final Throwable error, final Instant timestamp, final P parameterObject) {
        this.error = error;
        this.timestamp = timestamp;
        this.parameterObject = parameterObject;
    }

    public Throwable getThrowable() {
        return error;
    }

    public P getParameterObject() {
        return parameterObject;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
