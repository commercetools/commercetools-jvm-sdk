package io.sphere.sdk.client;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;


//TODO move to sphere test helpers
public abstract class HttpClientTestDouble implements HttpClient {
    @Override
    public ListenableFuture<HttpResponse> execute(final Requestable requestable) {
        return Futures.immediateFuture(testDouble(requestable));
    }

    public abstract HttpResponse testDouble(final Requestable requestable);

    @Override
    public void close() {
    }
}
