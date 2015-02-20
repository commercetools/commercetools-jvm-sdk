package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;

import java.util.Optional;

final class HttpRequestImpl extends Base implements HttpRequest {
    private final HttpMethod httpMethod;
    private final String url;
    private final HttpHeaders headers;
    private final Optional<HttpRequestBody> body;

    HttpRequestImpl(final HttpMethod httpMethod, final String url, final HttpHeaders headers, final Optional<HttpRequestBody> body) {
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
    public Optional<HttpRequestBody> getBody() {
        return body;
    }
}