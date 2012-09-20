package de.commercetools.sphere.client.model;

/** Exception thrown on attempt to access an object inside a non expanded {@link Reference}. */
public class ReferenceException extends RuntimeException {

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