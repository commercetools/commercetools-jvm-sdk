package io.sphere.sdk.http;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.CompletableFuture;

@ThreadSafe
public interface HttpClient extends AutoCloseable {
    <T> CompletableFuture<HttpResponse> execute(Requestable request);

    void close();
}
