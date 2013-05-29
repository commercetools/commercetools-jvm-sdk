package io.sphere.client;

/** Exception thrown when the Sphere authorization service responds with other status code than HTTP 200 OK. */
public class AuthorizationException extends SphereClientException {
    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(Throwable cause) {
        super(cause);
    }
}
