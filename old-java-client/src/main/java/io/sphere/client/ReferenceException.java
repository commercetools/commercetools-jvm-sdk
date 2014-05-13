package io.sphere.client;

/** Exception thrown on attempt to access an object inside a non expanded {@link io.sphere.client.model.Reference}. */
public class ReferenceException extends SphereClientException {

    public ReferenceException(String message) {
        super(message);
    }

    public ReferenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReferenceException(Throwable cause) {
        super(cause);
    }
}
