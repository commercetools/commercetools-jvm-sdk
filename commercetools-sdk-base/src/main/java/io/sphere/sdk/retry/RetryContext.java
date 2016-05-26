package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Instant;

public interface RetryContext {
    Instant getStartTimestamp();

    Throwable getFirstError();

    Throwable getLatestError();

    @Nullable
    Object getFirstParameter();

    @Nullable
    Object getLatestParameter();

    Long getAttempt();
}
