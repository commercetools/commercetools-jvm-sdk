package io.sphere.sdk.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.SphereException;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.Optional;
import java.util.function.Function;

/**
 *
 * <span id="exception-summary">Exception thrown when SPHERE.IO responds<br>with a status code other than HTTP 2xx.</span>
 *
 */
public abstract class SphereServiceException extends SphereException {
    static final long serialVersionUID = 0L;
    private final int statusCode;

    public SphereServiceException(final int statusCode) {
        this.statusCode = statusCode;
    }

    public SphereServiceException(final String message, final int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public SphereServiceException(final Throwable cause, final int statusCode) {
        super(cause);
        this.statusCode = statusCode;
    }

    public SphereServiceException(final String message, final Throwable cause, final int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public final Optional<JsonNode> getJsonBody() {
        final Function<byte[], JsonNode> f = body -> SphereJsonUtils.toJsonNode(body);
        try {
            return httpResponse.flatMap(r -> r.getResponseBody().map(f));
        } catch (final Exception e) {
            SphereInternalLogger.getLogger(SphereServiceException.class).error(() -> "Cannot provide JSON body.", e);
            return Optional.empty();
        }
    }
}
