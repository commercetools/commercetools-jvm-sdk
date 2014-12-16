package io.sphere.sdk.http;

class ByteArrayBodyHttpRequestImpl extends HttpRequestImpl implements ByteArrayBodyHttpRequest {
    private final byte[] body;

    ByteArrayBodyHttpRequestImpl(final HttpMethod httpMethod, final String path, final String contentType, final byte[] body) {
        super(httpMethod, path, HttpHeaders.of("Content-Type", contentType));
        this.body = body;
    }

    @Override
    public byte[] getBody() {
        return body;
    }
}
