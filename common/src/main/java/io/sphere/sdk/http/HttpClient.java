package io.sphere.sdk.http;

import java.io.Closeable;
import java.util.concurrent.CompletionStage;

public interface HttpClient extends Closeable {
    CompletionStage<HttpResponse> execute(HttpRequest httpRequest);

    void close();
}
