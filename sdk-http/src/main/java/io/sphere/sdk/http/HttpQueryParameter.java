package io.sphere.sdk.http;

public final class HttpQueryParameter extends Base {
    private final String key;
    private final String value;

    private HttpQueryParameter(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static HttpQueryParameter of(final String key, final String value) {
        return new HttpQueryParameter(key, value);
    }
}
