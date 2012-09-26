package de.commercetools.sphere.client;

/** Exception thrown when the Sphere backend responds with other status code than HTTP 2xx. */
public class SphereException extends RuntimeException {

    public SphereException(String message) {
        super(message);
    }

    public SphereException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SphereException(Throwable cause) {
        super(cause);
    }
}
