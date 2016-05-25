package io.sphere.sdk.client;

import io.sphere.sdk.retry.AsyncRetrySupervisor;

import java.util.concurrent.CompletionStage;

final class RetrySphereClientImpl extends SphereClientDecorator {
    //TODO improvement could be to make it restarteable
//    private RetrySphereClient(final Supplier<SphereClient> delegate, final boolean restartable) {


    private final AsyncRetrySupervisor supervisor = null;


    RetrySphereClientImpl(final SphereClient delegate) {
        super(delegate);
    }

    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return supervisor.supervise(this, super::execute, sphereRequest);
    }
}
