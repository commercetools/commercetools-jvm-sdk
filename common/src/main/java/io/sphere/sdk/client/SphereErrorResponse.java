package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

/** Response object returned by the Sphere Projects Web Service in case of an error.
 *  @see <a href="http://sphere.io/dev/HTTP_API_Projects_Errors.html">API documentation</a> */
public class SphereErrorResponse {
    private final int statusCode;
    private final String message;
    private final List<SphereError> errors;

    @JsonCreator
    private SphereErrorResponse(final int statusCode, final String message, final List<SphereError> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors == null ? Collections.<SphereError>emptyList() : errors;
    }

    public int getStatusCode() { return statusCode; }

    /**
     * The message of the first error, for convenience.
     * @return the first error message
     */
    public String getMessage() { return message; }

    public List<SphereError> getErrors() { return errors; }

    @Override public String toString() {
        final List<SphereError> sphereErrors = getErrors();
        final String formattedErrors = sphereErrors.stream().map(x -> x.toString()).collect(joining("\n  "));
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

    public static TypeReference<SphereErrorResponse> typeReference() {
        return new TypeReference<SphereErrorResponse>() {
            @Override
            public String toString() {
                return "TypeReference<SphereErrorResponse>";
            }
        };
    }

    @JsonIgnore
    public static SphereErrorResponse of(final int statusCode, final String message, final List<SphereError> errors) {
        return new SphereErrorResponse(statusCode, message, errors);
    }
}
