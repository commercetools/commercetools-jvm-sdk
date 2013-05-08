package io.sphere.internal.request;

import com.ning.http.client.AsyncHttpClient;
import io.sphere.client.oauth.ClientCredentials;

/** Creates real GET and POST requests for {@link io.sphere.internal.request.RequestFactoryImpl}.
 *  Can be mocked in tests. */
public class BasicRequestFactoryImpl implements BasicRequestFactory {
    private final AsyncHttpClient httpClient;
    private final ClientCredentials credentials;

    public BasicRequestFactoryImpl(AsyncHttpClient httpClient, ClientCredentials credentials) {
        this.httpClient = httpClient;
        this.credentials = credentials;
    }

    public <T> RequestHolder<T> createGet(String url) {
        return new RequestHolderImpl<T>(SetCredentials.forRequest(httpClient.prepareGet(url), credentials));
    }

    public <T> RequestHolder<T> createPost(String url) {
        return new RequestHolderImpl<T>(
                SetCredentials.forRequest(httpClient.preparePost(url).setHeader("Content-Type", "application/json"), credentials));
    }
}
