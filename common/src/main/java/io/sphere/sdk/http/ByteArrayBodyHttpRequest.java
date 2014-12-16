package io.sphere.sdk.http;

public interface ByteArrayBodyHttpRequest extends HttpRequest {
    @Override
    HttpMethod getHttpMethod();

    @Override
    String getPath();

    byte[] getBody();
}
