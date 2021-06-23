package io.sphere.sdk.client;


import io.sphere.sdk.http.AsyncHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;


public final class SphereAsyncHttpClientFactory extends SphereHttpClientFactory {
    @Deprecated
    public static HttpClient create() {

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
