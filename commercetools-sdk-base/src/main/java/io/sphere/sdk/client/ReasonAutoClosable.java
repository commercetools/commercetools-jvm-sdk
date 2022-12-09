package io.sphere.sdk.client;

import javax.annotation.Nullable;

public interface ReasonAutoClosable extends AutoCloseable {
    public void close(final Throwable reason);

    @Nullable
    public Throwable getClosingReason();
}
