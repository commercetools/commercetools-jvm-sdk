package io.sphere.client;

/** Exception thrown by the Sphere Java client. */
public class SphereClientException extends RuntimeException {
    protected SphereClientException() {}

    public SphereClientException(String message) {
        super(message);
    }

    public SphereClientException(String message, Throwable cause) {
        super(message + ": " + cause.getMessage(), cause);
    }
    
    public SphereClientException(Throwable cause) {
        this(cause.getMessage(), cause);
    }
}
