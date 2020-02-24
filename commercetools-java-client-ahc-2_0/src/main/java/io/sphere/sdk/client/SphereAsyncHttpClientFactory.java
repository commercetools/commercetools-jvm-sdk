package io.sphere.sdk.client;

import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.models.Base;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;

/**
 * Creates an {@link HttpClient} with a commercetools configured underlying {@link org.asynchttpclient.AsyncHttpClient}.
 */
public final class SphereAsyncHttpClientFactory extends SphereHttpClientFactory {
    @Deprecated
    public static HttpClient create() {
        /*
        https://static.javadoc.io/org.asynchttpclient/async-http-client/2.0.0-RC9/org/asynchttpclient/AsyncHttpClientConfig.html
         */
        final DefaultAsyncHttpClientConfig config = new DefaultAsyncHttpClientConfig.Builder()
                .setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1.2"})
                .setReadTimeout(121000)
                .setRequestTimeout(121000)
                .build();
        return AsyncHttpClientAdapter.of(new DefaultAsyncHttpClient(config));
    }

    @Override
    public  HttpClient getClient(){
        return create();
    }


}
