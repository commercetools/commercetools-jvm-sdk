package io.sphere.sdk.http;

public interface JsonBodyHttpRequest extends HttpRequest {
    @Override
    HttpMethod getHttpMethod();

    @Override
    String getPath();

    String getBody();
}
