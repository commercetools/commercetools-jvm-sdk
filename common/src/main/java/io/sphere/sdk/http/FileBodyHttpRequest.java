package io.sphere.sdk.http;

import java.io.File;

public interface FileBodyHttpRequest extends HttpRequest {
    @Override
    HttpMethod getHttpMethod();

    @Override
    String getPath();

    File getBody();
}
