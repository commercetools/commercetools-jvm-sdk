package io.sphere.sdk.client;

import io.sphere.sdk.http.*;
import io.sphere.sdk.models.Base;

import java.io.File;
import java.util.Optional;

import static io.sphere.sdk.http.HttpHeaders.*;

public class HttpRequestIntent extends Base {
    private final HttpMethod httpMethod;
    private final String path;
    private final HttpHeaders headers;
    private final Optional<HttpRequestBody> body;

    private HttpRequestIntent(final HttpMethod httpMethod, final String path, final HttpHeaders headers, final Optional<HttpRequestBody> body) {
        this.headers = headers;
        this.httpMethod = httpMethod;
        this.path = path;
        this.body = body;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Optional<HttpRequestBody> getBody() {
        return body;
    }

    public HttpRequestIntent plusHeader(final String name, final String value) {
        return HttpRequestIntent.of(getHttpMethod(), getPath(), getHeaders().plus(name, value), getBody());
    }

    public HttpRequestIntent prefixPath(final String prefix) {
        return HttpRequestIntent.of(getHttpMethod(), prefix + getPath(), getHeaders(), getBody());
    }

    public HttpRequest toHttpRequest(final String baseUrl) {
        return HttpRequest.of(getHttpMethod(), baseUrl + getPath(), getHeaders(), getBody());
    }

    public static HttpRequestIntent of(final HttpMethod httpMethod, final String path) {
        return of(httpMethod, path, HttpHeaders.of(), Optional.<HttpRequestBody>empty());
    }

    public static HttpRequestIntent of(final HttpMethod httpMethod, final String path, final HttpHeaders headers, final Optional<HttpRequestBody> body) {
        return new HttpRequestIntent(httpMethod, path, headers, body);
    }

    public static HttpRequestIntent of(final HttpMethod httpMethod, final String path, final String body) {
        return of(httpMethod, path, HttpHeaders.of(), Optional.of(StringHttpRequestBody.of(body)));
    }

    public static HttpRequestIntent of(final HttpMethod httpMethod, final String path, final File body, final String contentType) {
        return of(httpMethod, path, HttpHeaders.of(CONTENT_TYPE, contentType), Optional.of(FileHttpRequestBody.of(body)));
    }
}
