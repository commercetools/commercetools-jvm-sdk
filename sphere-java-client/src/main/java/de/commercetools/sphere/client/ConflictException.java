package de.commercetools.sphere.client;

/** Exception thrown when the Sphere backend responds with HTTP 409 Conflict. */
public class ConflictException extends SphereBackendException {

    public ConflictException(String requestUrl, String responseBody) {
        super(409, requestUrl, responseBody);
    }
}
