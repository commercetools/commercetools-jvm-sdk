package io.sphere.sdk.http;

import java.util.concurrent.CompletableFuture;


//TODO move to sphere test helpers
public abstract class HttpClientTestDouble implements HttpClient {
    @Override
    public CompletableFuture<HttpResponse> execute(final Requestable requestable) {
        return CompletableFuture.completedFuture(testDouble(requestable));
    }

    public abstract HttpResponse testDouble(final Requestable requestable);

    @Override
    public void close() {
    }
}
