package io.sphere.sdk.client;


import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;

public class NingAsyncHttpClientHttpRequestExecutor implements HttpRequestExecutor {
    private final Config config;

    public NingAsyncHttpClientHttpRequestExecutor(Config config) {
        this.config = config;
    }

    @Override
    public <T> ListenableFuture<HttpResponse> execute(Requestable<T> request) {
        request.httpRequest();//TODO
        return null;
    }
}
