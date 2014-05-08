package io.sphere.sdk.client;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;


//TODO move to sphere test helpers
public abstract class HttpClientTestDouble implements HttpClient {
    @Override
    public <T> ListenableFuture<HttpResponse> execute(final Requestable<T> requestable) {
        return Futures.immediateFuture(testDouble(requestable));
    }

    public abstract <T> HttpResponse testDouble(final Requestable<T> requestable);
}
