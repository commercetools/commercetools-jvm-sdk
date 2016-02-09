package io.sphere.sdk.client;

import io.sphere.sdk.client.QueueSphereClientDecoratorActor.AsyncTask;
import io.sphere.sdk.utils.CompletableFutureUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Decorates a {@link SphereClient} to limit the amount of parallel requests which await an answer.
 * If {@code maxParallelRequests} are waiting for a response every further request will be added to an unbound queue.
 * Then the next answer will cause that the first request in the queue will be executed.
 *
 */
public final class QueueSphereClientDecorator extends SphereClientDecorator implements SphereClient {
    private final Actor actor;
    private final boolean closeUnderlyingClient;

    private QueueSphereClientDecorator(final SphereClient delegate, final int maxParallelRequests, final boolean closeUnderlyingClient) {
        super(delegate);
        this.closeUnderlyingClient = closeUnderlyingClient;
        this.actor = new QueueSphereClientDecoratorActor(maxParallelRequests);
    }


    @Override
    public <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        final CompletableFuture<T> promiseForTheClient = new CompletableFuture<>();
        final AsyncTask asyncTask = new AsyncTask(() -> {
            final CompletionStage<T> realFuture = super.execute(sphereRequest);
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
        if (closeUnderlyingClient) {
            super.close();
        }
    }

    public static SphereClient of(final SphereClient delegate, final int maxParallelRequests, final boolean closeUnderlyingClient) {
        return new QueueSphereClientDecorator(delegate, maxParallelRequests, closeUnderlyingClient);
    }

    public static SphereClient of(final SphereClient delegate, final int maxParallelRequests) {
        return of(delegate, maxParallelRequests, true);
    }
}
