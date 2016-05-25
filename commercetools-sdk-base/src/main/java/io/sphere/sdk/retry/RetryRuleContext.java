package io.sphere.sdk.retry;

import javax.annotation.Nullable;

public interface RetryRuleContext {
    Throwable getError();

    @Nullable
    Object getParameter();

    Long getAttempt();
}
