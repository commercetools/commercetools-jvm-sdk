package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;

class HttpRequestImpl extends Base implements HttpRequest {
    private final HttpMethod httpMethod;
    private final String path;
    private final HttpHeaders headers;

    HttpRequestImpl(final HttpMethod httpMethod, final String path, final HttpHeaders headers) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.headers = headers;
    }

    HttpRequestImpl(final HttpMethod httpMethod, final String path) {
        this(httpMethod, path, HttpHeaders.of());
    }

    @Override
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
