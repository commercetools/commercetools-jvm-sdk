package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpHeaders;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpRequestBody;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;

/**
 * Expresses the http request domain model form commercetools platform as a draft for {@link HttpRequest}.
 */
final class HttpRequestIntentImpl extends Base implements HttpRequestIntent {
    private final HttpMethod httpMethod;
    private final String path;
    private final HttpHeaders headers;
    @Nullable
    private final HttpRequestBody body;

    HttpRequestIntentImpl(final HttpMethod httpMethod, final String path, final HttpHeaders headers, @Nullable final HttpRequestBody body) {
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

    @Nullable
    public HttpRequestBody getBody() {
        return body;
    }

    public HttpRequestIntentImpl plusHeader(final String name, final String value) {
        return new HttpRequestIntentImpl(getHttpMethod(), getPath(), getHeaders().plus(name, value), getBody());
    }

    public HttpRequestIntentImpl prefixPath(final String prefix) {
        return new HttpRequestIntentImpl(getHttpMethod(), prefix + getPath(), getHeaders(), getBody());
    }

    public HttpRequest toHttpRequest(final String baseUrl) {
        return HttpRequest.of(getHttpMethod(), baseUrl + getPath(), getHeaders(), getBody());
    }
}
