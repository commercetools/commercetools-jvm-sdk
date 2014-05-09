package io.sphere.sdk.client;


import com.google.common.util.concurrent.ListenableFuture;
import com.typesafe.config.Config;

public class NingAsyncHttpClientHttpClient implements HttpClient {

    public NingAsyncHttpClientHttpClient(final Config config) {
    }

    @Override
    public <T> ListenableFuture<HttpResponse> execute(final Requestable<T> request) {
        request.httpRequest();//TODO
        return null;
    }
}
