package io.sphere.sdk.client;

import javax.annotation.Nonnull;
import java.util.List;

/** Generic exception thrown when a Sphere web service responds with a status code other than HTTP 2xx. */
public class SphereBackendException extends SphereException {
    private static final long serialVersionUID = 0L;

    private final String requestUrl;
    private final SphereErrorResponse errorResponse;

    public SphereBackendException(String requestUrl, @Nonnull SphereErrorResponse errorResponse) {
        super(String.format("Error response from Sphere: %s\n%s", requestUrl, errorResponse));
        if (errorResponse == null) throw new NullPointerException("errorResponse");
        this.errorResponse = errorResponse;
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() { return requestUrl; }

    public int getStatusCode() { return errorResponse.getStatusCode(); }

    public String getMessage() { return errorResponse.getMessage(); }

    @Nonnull public List<SphereError> getErrors() { return errorResponse.getErrors(); }

    @Override
    public String toString() {
        return "SphereBackendException{" +
                "requestUrl='" + requestUrl + '\'' +
                ", errorResponse=" + errorResponse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SphereBackendException)) return false;

        SphereBackendException that = (SphereBackendException) o;

        if (errorResponse != null ? !errorResponse.equals(that.errorResponse) : that.errorResponse != null)
            return false;
        if (requestUrl != null ? !requestUrl.equals(that.requestUrl) : that.requestUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestUrl != null ? requestUrl.hashCode() : 0;
        result = 31 * result + (errorResponse != null ? errorResponse.hashCode() : 0);
        return result;
    }
}
