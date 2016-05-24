package io.sphere.sdk.retry;

import javax.annotation.Nullable;
import java.time.Instant;

public interface AttemptErrorResult<P> {
    Throwable getThrowable();

    Instant getTimestamp();

    @Nullable
    P getParameterObject();
    //TODO check generics typing
}
