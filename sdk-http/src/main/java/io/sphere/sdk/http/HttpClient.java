package io.sphere.sdk.http;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.util.concurrent.CompletionStage;

public interface HttpClient extends Closeable {
    CompletionStage<HttpResponse> execute(HttpRequest httpRequest);

    @Nullable
    default String getUserAgent() {
        return null;
    }

    @Override
    void close();
}
