package io.sphere.sdk.client;

import io.sphere.sdk.retry.RetryRule;

import java.util.List;

/**
 * Wraps a {@link SphereClient} to handle failures like gateway timeouts and version conflicts through retrying the request.
 * <p id="retry-gateway-timeout">A best practice example to retry on gateway timeouts and similar problems</p>
 * {@include.example io.sphere.sdk.client.retry.RetryBadGatewayExample}
 *
 * <p id="retry-delete-on-version-conflict">Not best practice but a lot of people requested to retry deletes on version conflicts:</p>
 * {@include.example io.sphere.sdk.client.retry.RetryDeleteExample}
 *
 */
public interface RetrySphereClientDecorator extends SphereClient {
    static SphereClient of(final SphereClient delegate, final List<RetryRule> retryRules) {
        return new RetrySphereClientImpl(delegate, retryRules);
    }
}
