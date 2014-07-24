package io.sphere.sdk.client;

import io.sphere.sdk.requests.HttpResponse;
import io.sphere.sdk.requests.Requestable;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.CompletableFuture;

@ThreadSafe
public interface HttpClient extends AutoCloseable {
    <T> CompletableFuture<HttpResponse> execute(Requestable request);

    void close();
}
