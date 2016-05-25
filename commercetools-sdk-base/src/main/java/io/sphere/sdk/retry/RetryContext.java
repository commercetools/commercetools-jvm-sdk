package io.sphere.sdk.retry;

import javax.annotation.Nullable;

public interface RetryContext {
    Throwable getError();

    @Nullable
    Object getParameter();

    Long getAttempt();
}
