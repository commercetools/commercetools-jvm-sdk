package io.sphere.sdk.client;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.function.Supplier;

abstract class AutoCloseableService extends Base implements AutoCloseable {


    protected AutoCloseableService() {
        log(() -> "Creating " + getLogName());
    }

    private void log(final Supplier<Object> message) {
        SphereInternalLogger.getLogger(this.getClass()).trace(message);
    }

    private String getLogName() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public final synchronized void close() {
        try {
            internalClose();
        } finally {
            log(() -> "Closing " + getLogName());
        }
    }

    protected abstract void internalClose();

    static void closeQuietly(final AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final Exception e) {
            SphereInternalLogger.getLogger("io").error(() -> "Error on closing resource.", e);
        }
    }
}