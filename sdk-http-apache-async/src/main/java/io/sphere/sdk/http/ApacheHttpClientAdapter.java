package io.sphere.sdk.http;

import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;

/**
 * Adapter to use a {@link CloseableHttpAsyncClient} as {@link HttpClient}.
 *
 * For more information about <a href="https://hc.apache.org" target="_blank">HttpComponents AsyncClient use their webpage</a>.
 */
public interface ApacheHttpClientAdapter extends HttpClient {

    static HttpClient of(final CloseableHttpAsyncClient client) {
        return ApacheHttpClientAdapterImpl.of(client);
    }
}
