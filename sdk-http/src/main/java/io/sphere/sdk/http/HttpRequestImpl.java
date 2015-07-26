package io.sphere.sdk.http;

import javax.annotation.Nullable;

final class HttpRequestImpl extends Base implements HttpRequest {
    private final HttpMethod httpMethod;
    private final String url;
    private final HttpHeaders headers;
    @Nullable
    private final HttpRequestBody body;

    HttpRequestImpl(final HttpMethod httpMethod, final String url, final HttpHeaders headers, @Nullable final HttpRequestBody body) {
        this.httpMethod = httpMethod;
        this.url = url;
        this.headers = headers;
        this.body = body;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }

    @Override
    @Nullable
    public HttpRequestBody getBody() {
        return body;
    }
}