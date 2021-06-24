package io.sphere.sdk.client;

import io.sphere.sdk.http.ApacheHttpClientAdapter;
import io.sphere.sdk.http.HttpClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;

public final class SphereApacheHttpClientFactory extends SphereHttpClientFactory {

    public SphereApacheHttpClientFactory(){

    }

    public static HttpClient create() {
        return ApacheHttpClientAdapter.of(HttpAsyncClients.createDefault());
    }

    @Override
    public  HttpClient getClient(){
        return create();
    }

}
