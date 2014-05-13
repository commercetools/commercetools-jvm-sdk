package io.sphere.sdk.client;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final String path;

    private HttpRequest(final HttpMethod httpMethod, final String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public static HttpRequest of(final HttpMethod httpMethod, final String url) {
        return new HttpRequest(httpMethod, url);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }
}
