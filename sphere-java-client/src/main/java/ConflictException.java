package de.commercetools.sphere.client;

/** Exception thrown when the Sphere backend responds with HTTP 409 Conflict. */
public class ConflictException extends BackendException {

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictException(Throwable cause) {
        super(cause);
    }
}
