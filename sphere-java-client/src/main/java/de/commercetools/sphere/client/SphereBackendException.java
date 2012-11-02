package de.commercetools.sphere.client;

/** Exception thrown when the Sphere backend responds with other status code than HTTP 2xx. */
public class SphereBackendException extends SphereException {
    private final int statusCode;
    private final String requestUrl;
    private final String responseBody;

    public SphereBackendException(int statusCode, String requestUrl, String responseBody) {
        super(String.format("Response status %s from Sphere: %s\n%s", statusCode, requestUrl, responseBody));
        this.statusCode = statusCode;
        this.requestUrl = requestUrl;
        this.responseBody = responseBody;
    }


}
