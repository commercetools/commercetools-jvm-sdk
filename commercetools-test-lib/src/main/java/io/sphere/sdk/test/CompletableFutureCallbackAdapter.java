package io.sphere.sdk.test;



import io.sphere.sdk.models.Base;
import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.concurrent.CompletableFuture;

/**
 * This class was duplicated as a dependency for {@link IntegrationTestHttpClient}
 * to avoid creating cyclic dependencies in the API.
 */
final class CompletableFutureCallbackAdapter<T> extends Base implements FutureCallback<T> {
    private final CompletableFuture<T> future;

    public CompletableFutureCallbackAdapter(final CompletableFuture<T> future) {
        this.future = future;
    }

    @Override
    public void cancelled() {
        future.cancel(true);
    }

    @Override
    public void completed(final T result) {
        future.complete(result);
    }

    @Override
    public void failed(final Exception e) {
        future.completeExceptionally(e);
    }
}
