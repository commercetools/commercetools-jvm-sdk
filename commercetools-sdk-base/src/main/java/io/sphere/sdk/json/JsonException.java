package io.sphere.sdk.json;

import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.http.HttpResponse;

/**
 * Exception concerning JSON.
 *
 * This may occur by parsing JSON from the platform and the POJO mapping does not work correctly.
 *
 * <h3>No suitable constructor</h3>
 * {@code detailMessage: com.fasterxml.jackson.databind.JsonMappingException:
 * No suitable constructor found for type [simple type, class a.full.ClassName]:
 * can not instantiate from JSON object (missing default constructor or creator,
 * or perhaps need to add/enable type information?)}
 *
 * Solution, add {@link com.fasterxml.jackson.annotation.JsonCreator} to the constructor of the class a.full.ClassName.
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
