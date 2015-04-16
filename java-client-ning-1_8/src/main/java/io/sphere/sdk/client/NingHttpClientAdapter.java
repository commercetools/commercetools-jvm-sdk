package io.sphere.sdk.client;

import com.ning.http.client.*;
import io.sphere.sdk.http.*;

public interface NingHttpClientAdapter extends HttpClient {

    public static HttpClient of() {
        return of(new AsyncHttpClient());
    }

    public static HttpClient of(final AsyncHttpClient asyncHttpClient) {
        return NingHttpClientAdapterImpl.of(asyncHttpClient);
    }
}