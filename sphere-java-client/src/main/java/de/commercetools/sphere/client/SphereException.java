package de.commercetools.sphere.client;

/** Exception thrown by the Sphere Java client. */
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
