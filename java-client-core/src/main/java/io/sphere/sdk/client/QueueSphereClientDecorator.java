package io.sphere.sdk.client;

import io.sphere.sdk.client.QueueSphereClientDecoratorActor.AsyncTask;

import java.util.concurrent.CompletableFuture;

public final class QueueSphereClientDecorator extends SphereClientDecorator implements SphereClient {
    private final Actor actor;

    private QueueSphereClientDecorator(final SphereClient delegate, final int maxParallelRequests) {
        super(delegate);
        this.actor = new QueueSphereClientDecoratorActor(maxParallelRequests);
    }


    @Override
    public <T> CompletableFuture<T> execute(final SphereRequest<T> sphereRequest) {
        final CompletableFuture<T> promiseForTheClient = new CompletableFuture<>();
        final AsyncTask asyncTask = new AsyncTask(() -> {
            final CompletableFuture<T> realFuture = super.execute(sphereRequest);
            CompletableFutureUtils.transferResult(realFuture, promiseForTheClient);
            CompletableFutureUtils.transferResult(realFuture, promiseForTheClient);
            CompletableFuture<String> forHandlerFuture = new CompletableFuture<>();
            realFuture.whenComplete((v, e) -> forHandlerFuture.complete("done"));
            return forHandlerFuture;
        });
        actor.tell(asyncTask);
        return promiseForTheClient;
    }

    @Override
    public void close() {
        AutoCloseableService.closeQuietly(actor);
        super.close();
    }

    public static SphereClient of(final SphereClient delegate, final int maxParallelRequests) {
        return new QueueSphereClientDecorator(delegate, maxParallelRequests);
    }
}
