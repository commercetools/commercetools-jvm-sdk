package io.sphere.sdk.http;

public interface NameValuePair {
    String getName();

    String getValue();

    static NameValuePair of(final String name, final String value) {
        return new NameValuePairImpl(name, value);
    }
}
