package io.sphere.sdk.client;

import io.sphere.sdk.http.*;

import javax.annotation.Nullable;
import java.io.File;

import static io.sphere.sdk.http.HttpHeaders.CONTENT_TYPE;

/**
 * Expresses the http request domain model form commercetools platform as a draft for {@link HttpRequest}.
 */
public interface HttpRequestIntent {
    HttpHeaders getHeaders();

    HttpMethod getHttpMethod();

    String getPath();

    @Nullable
    HttpRequestBody getBody();

    HttpRequestIntent plusHeader(final String name, final String value);

    HttpRequestIntent prefixPath(final String prefix);

    HttpRequest toHttpRequest(final String baseUrl);

    static HttpRequestIntent of(final HttpMethod httpMethod, final String path) {
        return of(httpMethod, path, HttpHeaders.of(), null);
    }

    static HttpRequestIntent of(final HttpMethod httpMethod, final String path, final HttpHeaders headers, @Nullable final HttpRequestBody body) {
        return new HttpRequestIntentImpl(httpMethod, path, headers, body);
    }

    static HttpRequestIntent of(final HttpMethod httpMethod, final String path, final String body) {
        return of(httpMethod, path, HttpHeaders.of(), StringHttpRequestBody.of(body));
    }

    static HttpRequestIntent of(final HttpMethod httpMethod, final String path, final File body, final String contentType) {
        return of(httpMethod, path, HttpHeaders.of(CONTENT_TYPE, contentType), FileHttpRequestBody.of(body));
    }
}
