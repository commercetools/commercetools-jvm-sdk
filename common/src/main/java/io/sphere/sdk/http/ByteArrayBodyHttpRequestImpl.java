package io.sphere.sdk.http;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

final class ByteArrayBodyHttpRequestImpl extends HttpRequestImpl implements ByteArrayBodyHttpRequest {
    private final byte[] body;

    ByteArrayBodyHttpRequestImpl(final HttpMethod httpMethod, final String path, final String contentType, final byte[] body) {
        super(httpMethod, path, HttpHeaders.of("Content-Type", contentType));
        this.body = body;
    }

    @Override
    public byte[] getBody() {
        return body;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).setExcludeFieldNames("body").toString();
    }
}
