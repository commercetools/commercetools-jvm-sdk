package io.sphere.sdk.http;

import java.io.Closeable;
import java.util.concurrent.CompletableFuture;

public interface HttpClient extends Closeable {
    CompletableFuture<HttpResponse> execute(HttpRequest httpRequest);

    void close();
}
