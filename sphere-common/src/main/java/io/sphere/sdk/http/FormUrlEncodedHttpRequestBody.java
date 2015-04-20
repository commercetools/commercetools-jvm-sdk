package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;

import java.util.Map;

public class FormUrlEncodedHttpRequestBody extends Base implements HttpRequestBody {
    private final Map<String, String> data;

    private FormUrlEncodedHttpRequestBody(final Map<String, String> data) {
        this.data = data;
    }

    public static FormUrlEncodedHttpRequestBody of(final Map<String, String> data) {
        return new FormUrlEncodedHttpRequestBody(data);
    }

    public Map<String, String> getData() {
        return data;
    }
}