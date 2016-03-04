package io.sphere.sdk.client;

import io.sphere.sdk.models.SphereException;

import java.util.concurrent.TimeoutException;

/**
 * Unchecked exceptions for timeouts in the JVM SDK. {@link io.sphere.sdk.http.HttpClient}s won't throw this exception but {@link io.sphere.sdk.http.HttpException}.
 * <p>The {@link #getCause()} most likely will provide a {@link TimeoutException}.
 */
public class SphereTimeoutException extends SphereException {
    static final long serialVersionUID = 0L;

    public SphereTimeoutException(final TimeoutException cause) {
        super(cause.getMessage(), cause);
    }
}
