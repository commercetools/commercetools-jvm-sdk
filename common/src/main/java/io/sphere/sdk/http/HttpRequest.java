package io.sphere.sdk.http;

import java.util.Optional;

import io.sphere.sdk.models.Base;
import net.jcip.annotations.Immutable;

@Immutable
public class HttpRequest extends Base implements Requestable {
    private final HttpMethod httpMethod;
    private final String path;
    private final Optional<String> body;

    private HttpRequest(final HttpMethod httpMethod, final String path) {
        this(httpMethod, path, Optional.<String>empty());
    }

    private HttpRequest(final HttpMethod httpMethod, final String path, final Optional<String> body) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.body = body;
    }

    public static HttpRequest of(final HttpMethod httpMethod, final String path) {
        return new HttpRequest(httpMethod, path);
    }

    public static HttpRequest of(final HttpMethod httpMethod, final String path, final String body) {
        return new HttpRequest(httpMethod, path, Optional.of(body));
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public Optional<String> getBody() {
        return body;
    }

    @Override
    public HttpRequest httpRequest() {
        return this;
    }
}
