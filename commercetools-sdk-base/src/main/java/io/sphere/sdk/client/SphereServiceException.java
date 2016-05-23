package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.SphereException;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 *
 * <span id="exception-summary">Exception thrown when the platform responds<br>with a status code other than HTTP 2xx.</span>
 *
 * <p>For inspiration how to create exceptions in unit tests:</p>
 *
 * {@include.example io.sphere.sdk.errors.ServerErrorExceptionDemoTest}
 *
 */
public abstract class SphereServiceException extends SphereException {
    static final long serialVersionUID = 0L;
    private final Integer statusCode;

    public SphereServiceException(final Integer statusCode) {
        this.statusCode = statusCode;
    }

    public SphereServiceException(final String message, final Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public SphereServiceException(final Throwable cause, final Integer statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public SphereServiceException(final String message, final Throwable cause, final Integer statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @Nullable
    public final JsonNode getJsonBody() {
        return Optional.ofNullable(httpResponse)
                .map(r -> r.getResponseBody())
                .map(body -> SphereJsonUtils.parse(body))
                .orElse(null);
    }
}
