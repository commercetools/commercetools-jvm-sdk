package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpResponse;

public class EmptyHttpBodyException extends SphereClientException {
    private static final long serialVersionUID = 0L;

    public EmptyHttpBodyException(final HttpResponse httpResponse) {
        super("There is no response body in " + httpResponse);
    }
}
