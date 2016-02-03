package io.sphere.sdk.http;

public final class HttpQueryParameter implements NameValuePair {
    private final String name;
    private final String value;

    private HttpQueryParameter(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static HttpQueryParameter of(final String name, final String value) {
        return new HttpQueryParameter(name, value);
    }
}
