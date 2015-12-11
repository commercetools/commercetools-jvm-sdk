package io.sphere.sdk.client;

import io.sphere.sdk.models.SphereException;

import java.util.concurrent.TimeoutException;

public class SphereTimeoutException extends SphereException {
    static final long serialVersionUID = 0L;

    public SphereTimeoutException(final TimeoutException cause) {
        super(cause.getMessage(), cause);
    }
}
