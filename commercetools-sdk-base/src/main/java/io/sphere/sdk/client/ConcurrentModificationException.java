package io.sphere.sdk.client;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.models.errors.ConcurrentModificationError;
import io.sphere.sdk.models.errors.ErrorResponse;
import io.sphere.sdk.models.errors.SphereError;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * HTTP code 409 response.
 *
 * <p>This error occurs if you try to update or delete a resource using an outdated version ({@link Versioned#getVersion()}).</p>
 *
 * <p>Do not confuse it with {@code java.util.ConcurrentModificationException}, especially in imports.</p>
 *
 * <p>Example how this could happen:</p>
 *
 * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#demoForFailing409IsGood()}
 *
 * <p>In case the update or deletion should executed in the same shape anyway you could get the current version and try to execute again:</p>
 * <p>Current version from exception:</p>
 * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#demoForBruteForceSolveTheVersionConflictWithExceptionCurrentVersion()}
 * <p>Current version by executing a get request:</p>
 * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#demoForBruteForceSolveTheVersionConflict()}
 *
 */
public class ConcurrentModificationException extends ClientErrorException implements ErrorResponse {
    private static final long serialVersionUID = 0L;

    private final List<? extends SphereError> errors;

    public ConcurrentModificationException() {
        super(409);
        errors = Collections.emptyList();
    }

    public ConcurrentModificationException(final ErrorResponse errorResponse) {
        this(errorResponse.getStatusCode(), errorResponse.getMessage(), errorResponse.getErrors());
    }

    ConcurrentModificationException(final Integer statusCode, final String message, final List<? extends SphereError> errors) {
        super(message, statusCode);
        this.errors = errors == null ? Collections.<SphereError>emptyList() : errors;
    }

    @Override
    public List<? extends SphereError> getErrors() {
        return errors;
    }

    /**
     * Gets the version of the object at the time of the failed command.
     *
     * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#concurrentModification()}
     *
     * @return version or null
     */
    @Nullable
    public Long getCurrentVersion() {
        final List<? extends SphereError> errors = getErrors();
        return ObjectUtils.defaultIfNull(errors, Collections.emptyList()).stream()
                .map(errror -> (SphereError) errror)
                .filter(error -> ConcurrentModificationError.CODE.equals(error.getCode()))
                .map(error -> error.as(ConcurrentModificationError.class).getCurrentVersion())
                .findFirst()
                .orElse(null);
    }
}
