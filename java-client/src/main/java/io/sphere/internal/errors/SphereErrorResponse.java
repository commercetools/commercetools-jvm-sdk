package io.sphere.internal.errors;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import io.sphere.client.SphereError;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Response object returned by the Sphere Projects Web Service in case of an error.
 *
 * @see <a href="http://sphere.io/dev/HTTP_API_Projects_Errors.html">API documentation</a>
 */
public class SphereErrorResponse {
    private int statusCode;
    private String message = "";
    @Nonnull
    private List<SphereError> errors = ImmutableList.of();

    // for JSON deserializer
    private SphereErrorResponse() {
    }

    public SphereErrorResponse(int statusCode, String message, @Nonnull List<SphereError> errors) {
        this.statusCode = statusCode;
        this.message = message;
        this.errors = errors;
    }

    /**
     * The HTTP status code.
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * The message of the first error, for convenience.
     */
    public String getMessage() {
        return message;
    }

    /**
     * The individual errors.
     */
    @Nonnull
    public List<SphereError> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SphereErrorResponse)) return false;

        SphereErrorResponse that = (SphereErrorResponse) o;

        if (statusCode != that.statusCode) return false;
        if (!errors.equals(that.errors)) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusCode;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + errors.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SphereErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", errors=" + Joiner.on("\n  ").join(getErrors()) +
                '}';
    }
}
