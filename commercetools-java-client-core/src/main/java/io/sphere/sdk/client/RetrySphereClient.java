package io.sphere.sdk.client;

import io.sphere.sdk.retry.RetryRule;

import java.util.List;

public interface RetrySphereClient extends SphereClient {
    static SphereClient of(final SphereClient delegate, final List<RetryRule> retryRules) {
        return new RetrySphereClientImpl(delegate, retryRules);
    }
}
