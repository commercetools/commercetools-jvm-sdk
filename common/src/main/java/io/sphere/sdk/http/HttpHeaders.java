package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;

import java.util.*;

import static io.sphere.sdk.utils.MapUtils.mapOf;
import static java.util.Collections.unmodifiableMap;

public class HttpHeaders extends Base {
    private final Map<String, String> headers;

    private HttpHeaders(final Map<String, String> headers) {
        this.headers = unmodifiableMap(headers);
    }

    private HttpHeaders(final String key, final String value) {
        this(mapOf(key, value));
    }

    public static HttpHeaders of(final String key, final String value) {
        return new HttpHeaders(key, value);
    }

    public static HttpHeaders of() {
        return new HttpHeaders(Collections.emptyMap());
    }

    public Optional<String> getFlatHeader(final String key) {
        return Optional.ofNullable(headers.get(key));
    }
}
