package io.sphere.sdk.client;

import io.sphere.sdk.requests.CommandImpl;

public class NotFoundException extends SphereClientException {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }

    public NotFoundException(Object o) {
        this(o.toString());
    }
}
