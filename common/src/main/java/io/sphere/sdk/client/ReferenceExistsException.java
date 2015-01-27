package io.sphere.sdk.client;

public class ReferenceExistsException extends SphereBackendException {
    private static final long serialVersionUID = 0L;

    public ReferenceExistsException(final String requestUrl, final SphereErrorResponse errorResponse) {
        super(requestUrl, errorResponse);
    }
}
