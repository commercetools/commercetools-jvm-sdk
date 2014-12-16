package io.sphere.sdk.http;

public interface HttpRequest extends Requestable {
    HttpMethod getHttpMethod();

    String getPath();

    HttpHeaders getHeaders();

    @Override
    default HttpRequest httpRequest() {
        return this;
    }

    public static HttpRequest of(final HttpMethod httpMethod, final String path) {
        return new HttpRequestImpl(httpMethod, path);
    }

    public static JsonBodyHttpRequest of(final HttpMethod httpMethod, final String path, final String body) {
        return new JsonBodyHttpRequestImpl(httpMethod, path, body);
    }

    public static ByteArrayBodyHttpRequest of(final HttpMethod httpMethod, final String path, final byte[] body, final String contentType) {
        return new ByteArrayBodyHttpRequestImpl(httpMethod, path, contentType, body);
    }
}
