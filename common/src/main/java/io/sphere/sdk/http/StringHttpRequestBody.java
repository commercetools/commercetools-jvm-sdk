package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;

public class StringHttpRequestBody extends Base implements HttpRequestBody {
    private final String body;

    private StringHttpRequestBody(final String body) {
        this.body = body;
    }

    public static StringHttpRequestBody of(final String body) {
        return new StringHttpRequestBody(body);
    }

    public String getUnderlying() {
        return body;
    }
}