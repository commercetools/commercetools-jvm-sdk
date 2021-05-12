package io.sphere.sdk.http;

import org.apache.hc.core5.concurrent.FutureCallback;

import java.util.concurrent.CompletableFuture;

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
