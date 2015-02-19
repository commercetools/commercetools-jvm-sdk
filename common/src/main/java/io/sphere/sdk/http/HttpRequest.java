package io.sphere.sdk.http;

import java.io.File;

public interface HttpRequest extends Requestable {
    HttpMethod getHttpMethod();

    String getPath();

    HttpHeaders getHeaders();

    @Override
    default HttpRequest httpRequestIntent() {
        return this;
    }

    public static HttpRequest of(final HttpMethod httpMethod, final String path) {
        return new HttpRequestImpl(httpMethod, path);
    }

    public static JsonBodyHttpRequest of(final HttpMethod httpMethod, final String path, final String body) {
        return new JsonBodyHttpRequestImpl(httpMethod, path, body);
    }

    public static FileBodyHttpRequest of(final HttpMethod httpMethod, final String path, final File body, final String contentType) {
        return new FileBodyHttpRequestImpl(httpMethod, path, contentType, body);
    }
}
