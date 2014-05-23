package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;

/** Response object returned by the Sphere Projects Web Service in case of an error.
 *  @see <a href="http://sphere.io/dev/HTTP_API_Projects_Errors.html">API documentation</a> */
public class SphereErrorResponse {
    private int statusCode;
    private String message;
    private List<SphereError> errors;

    @JsonCreator
    public SphereErrorResponse(@JsonProperty("statusCode") final int statusCode,
                               @JsonProperty("message") final String message,
                               @JsonProperty("errors") final List<SphereError> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors == null ? ImmutableList.<SphereError>of() : errors;
    }

    /** The HTTP status code. */
    public int getStatusCode() { return statusCode; }

    /** The message of the first error, for convenience. */
    public String getMessage() { return message; }

    /** The individual errors. */
    public List<SphereError> getErrors() { return errors; }

    @Override public String toString() {
        final List<SphereError> sphereErrors = getErrors();
        final String formattedErrors = Joiner.on("\n  ").join(sphereErrors);
        return String.format("[" + getStatusCode() + "]" + "\n  " + formattedErrors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SphereErrorResponse)) return false;

        SphereErrorResponse that = (SphereErrorResponse) o;

        if (statusCode != that.statusCode) return false;
        if (errors != null ? !errors.equals(that.errors) : that.errors != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (errors != null ? errors.hashCode() : 0);
        return result;
    }
}
