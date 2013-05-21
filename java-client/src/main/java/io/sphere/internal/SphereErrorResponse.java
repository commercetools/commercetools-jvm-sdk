package io.sphere.internal;

import com.google.common.base.Joiner;
import io.sphere.internal.SphereError;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/** Response object returned by the Sphere Projects Web Service in case of an error.
 *  @see <a href="http://sphere.io/dev/HTTP_API_Projects_Errors.html">API documentation</a> */
public class SphereErrorResponse {
    private int statusCode;
    private String message;
    @Nonnull private List<SphereError> errors = new ArrayList<SphereError>();

    // for JSON deserializer
    private SphereErrorResponse() {}

    /** The HTTP status code. */
    public int getStatusCode() { return statusCode; }

    /** The message of the first error, for convenience. */
    public String getMessage() { return message; }

    /** The individual errors. */
    @Nonnull public List<SphereError> getErrors() { return errors; }

    @Override public String toString() {
        return String.format("[" + getStatusCode() + "]" + "\n  " + Joiner.on("\n  ").join(getErrors()));
    }
}
