package io.sphere.sdk.client;

public interface ReasonAutoClosable extends AutoCloseable {
    public void close(final Throwable reason);

    public Throwable getClosingReason();
}
