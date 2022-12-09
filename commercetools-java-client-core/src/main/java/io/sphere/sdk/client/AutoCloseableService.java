package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.function.Supplier;

public abstract class AutoCloseableService extends Base implements ReasonAutoClosable {
    private boolean closed = false;
    private Throwable closingReason;

    protected AutoCloseableService() {
        log(() -> "Creating " + getLogName());
    }

    private void log(final Supplier<Object> message) {
        SphereInternalLogger.getLogger(this.getClass()).trace(message);
    }

    private String getLogName() {
        return this.getClass().getCanonicalName();
    }

    public final synchronized void close(final Throwable reason) {
        this.closingReason = reason;
        close();
    }

    public Throwable getClosingReason() {
        if (isClosed()) {
            return closingReason;
        }
        return null;
    }

    @Override
    public final synchronized void close() {
        try {
            internalClose();
        } finally {
            closed = true;
            log(() -> "Closing " + getLogName());
        }
    }

    protected abstract void internalClose();

    protected final boolean isClosed() {
        return closed;
    }

    protected void rejectExcutionIfClosed(final String message) {
        if (isClosed()) {
            throw new IllegalStateException(message, closingReason);//rejection for execution so the exception will not be in the CompletionStage
        }
    }

    static void closeQuietly(final AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final Exception e) {
            SphereInternalLogger.getLogger(AutoCloseableService.class).error(() -> "Error on closing resource.", e);
        }
    }
}
