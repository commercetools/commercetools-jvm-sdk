package io.sphere.sdk.client;

public class ConcurrentModificationException extends SphereBackendException {
    private static final long serialVersionUID = 0L;

    public ConcurrentModificationException(final String requestUrl, final SphereErrorResponse errorResponse) {
        super(requestUrl, errorResponse);
    }
}
