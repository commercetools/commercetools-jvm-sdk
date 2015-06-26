package io.sphere.sdk.http;

import com.ning.http.client.*;

public interface NingHttpClientAdapter extends HttpClient {

    static HttpClient of() {
        return of(new AsyncHttpClient());
    }

    static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return NingHttpClientAdapterImpl.of(asyncHttpClient);
    }
}