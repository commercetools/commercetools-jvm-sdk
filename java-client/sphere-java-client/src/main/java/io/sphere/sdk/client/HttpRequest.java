package io.sphere.sdk.client;

public class HttpRequest {
    private final HttpMethod httpMethod;
    private final String url;

    private HttpRequest(HttpMethod httpMethod, String url) {
        this.httpMethod = httpMethod;
        this.url = url;
    }

    public static HttpRequest of(HttpMethod httpMethod, String url) {
        return new HttpRequest(httpMethod, url);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getUrl() {
        return url;
    }
}
