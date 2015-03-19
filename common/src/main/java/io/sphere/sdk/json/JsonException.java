package io.sphere.sdk.json;

import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.http.HttpResponse;

/**
 * Exception concerning JSON.
 *
 * This may occur by parsing JSON from SPHERE.IO and the POJO mapping does not work correctly.
 *
 */
public class JsonException extends SphereException {
    private static final long serialVersionUID = 0L;

    public JsonException(final Throwable cause) {
        super(cause);
    }

    public JsonException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public JsonException(final HttpResponse httpResponse) {
        super(httpResponse.toString());
    }
}
