package io.sphere.sdk.http;

class JsonBodyHttpRequestImpl extends HttpRequestImpl implements JsonBodyHttpRequest {
    private final String body;

    JsonBodyHttpRequestImpl(final HttpMethod httpMethod, final String path, final String body) {
        super(httpMethod, path);
        this.body = body;
    }

    @Override
    public String getBody() {
        return body;
    }
}
