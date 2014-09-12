package io.sphere.sdk.http;

import net.jcip.annotations.ThreadSafe;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

@ThreadSafe
public interface HttpClient extends Closeable {
    <T> CompletableFuture<HttpResponse> execute(Requestable request);

    void close();
}
