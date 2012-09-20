package de.commercetools.sphere.client;

/** Exception thrown when the Sphere backend responds with other status code than HTTP 2xx. */
public class BackendException extends RuntimeException {

    public BackendException(String message) {
        super(message);
    }

    public BackendException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BackendException(Throwable cause) {
        super(cause);
    }
}
