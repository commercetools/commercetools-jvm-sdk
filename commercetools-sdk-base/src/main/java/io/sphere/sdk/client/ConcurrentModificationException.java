package io.sphere.sdk.client;

import io.sphere.sdk.models.Versioned;

/**
 * HTTP code 409 response from SPHERE.IO.
 *
 * <p>This error occurs if you try to update or delete a resource using an outdated version ({@link Versioned#getVersion()}).</p>
 *
 * <p>Do not confuse it with {@code java.util.ConcurrentModificationException}, especially in imports.</p>
 *
 * <p>Example how this could happen:</p>
 *
 * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#demoForFailing409IsGood()}
 *
 * <p>In case the update or deletion shoulx executed in the same shape anyway you could get the current version and try to execute again:</p>
 * {@include.example io.sphere.sdk.errors.SphereExceptionIntegrationTest#demoForBruteForceSolveTheVersionConflict()}
 *
 */
public class ConcurrentModificationException extends ClientErrorException {
    private static final long serialVersionUID = 0L;

    public ConcurrentModificationException() {
        super(409);
    }
}
