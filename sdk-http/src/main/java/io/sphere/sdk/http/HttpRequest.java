package io.sphere.sdk.http;

import javax.annotation.Nullable;

public interface HttpRequest {
    HttpMethod getHttpMethod();

    String getUrl();

    HttpHeaders getHeaders();

    @Nullable
    HttpRequestBody getBody();

    static HttpRequest of(final HttpMethod httpMethod, final String url) {
        return of(httpMethod, url, HttpHeaders.empty(), null);
    }

    static HttpRequest of(final HttpMethod httpMethod, final String url, final HttpHeaders headers, @Nullable final HttpRequestBody body) {
        return new HttpRequestImpl(httpMethod, url, headers, body);
    }
}