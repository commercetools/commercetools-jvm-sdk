package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Instant;

public interface AttemptErrorResult<P> {
    Throwable getError();

    Instant getTimestamp();

    @Nullable
    P getParameter();
}
