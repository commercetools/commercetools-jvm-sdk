package io.sphere.sdk.http;

import java.util.Optional;

public interface HttpRequest {
    HttpMethod getHttpMethod();

    String getUrl();

    HttpHeaders getHeaders();

    Optional<HttpRequestBody> getBody();

    static HttpRequest of(final HttpMethod httpMethod, final String url) {
        return of(httpMethod, url, HttpHeaders.empty(), Optional.<HttpRequestBody>empty());
    }

    static HttpRequest of(final HttpMethod httpMethod, final String url, final HttpHeaders headers, final Optional<HttpRequestBody> body) {
        return new HttpRequestImpl(httpMethod, url, headers, body);
    }
}