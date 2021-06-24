package io.sphere.sdk.http;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

final class ResponseAsyncCompletionHandler extends AsyncCompletionHandler<Response> {
    private final CompletableFuture<Response> future;

    ResponseAsyncCompletionHandler(final CompletableFuture<Response> future) {
        this.future = future;
    }

    @Override
    public Response onCompleted(final Response response) throws Exception {
        future.complete(response);
        return response;
    }

    @Override
    public void onThrowable(final Throwable t) {
        future.completeExceptionally(t);
        super.onThrowable(t);
    }
}
