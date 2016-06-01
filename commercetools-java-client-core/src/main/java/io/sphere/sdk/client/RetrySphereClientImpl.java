package io.sphere.sdk.client;

import io.sphere.sdk.retry.AsyncRetrySupervisor;
import io.sphere.sdk.retry.RetryRule;

import java.util.List;
import java.util.concurrent.CompletionStage;

final class RetrySphereClientImpl extends SphereClientDecorator {
    private final AsyncRetrySupervisor supervisor;

    RetrySphereClientImpl(final SphereClient delegate, final List<RetryRule> retryRules) {
        super(delegate);
        this.supervisor = AsyncRetrySupervisor.of(retryRules);
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return supervisor.supervise(this, super::execute, sphereRequest);
    }

    @Override
    public void close() {
        supervisor.close();
        super.close();
    }
}
