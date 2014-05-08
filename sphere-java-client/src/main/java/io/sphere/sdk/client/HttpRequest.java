package io.sphere.sdk.client;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final String url;

    public HttpRequest(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }
}
