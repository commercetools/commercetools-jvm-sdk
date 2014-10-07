package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

public class QueryParameter extends Base {
    private final String key;
    private final String value;

    private QueryParameter(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static QueryParameter of(final String key, final String value) {
        return new QueryParameter(key, value);
    }
}
