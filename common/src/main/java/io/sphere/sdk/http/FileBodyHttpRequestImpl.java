package io.sphere.sdk.http;

import java.io.File;

final class FileBodyHttpRequestImpl extends HttpRequestImpl implements FileBodyHttpRequest {
    private final File body;

    FileBodyHttpRequestImpl(final HttpMethod httpMethod, final String path, final String contentType, final File body) {
        super(httpMethod, path, HttpHeaders.of("Content-Type", contentType));
        this.body = body;
    }

    @Override
    public File getBody() {
        return body;
    }
}
