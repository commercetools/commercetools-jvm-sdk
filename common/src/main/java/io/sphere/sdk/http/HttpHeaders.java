package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.MapUtils;

import java.util.*;

import static io.sphere.sdk.utils.MapUtils.copyOf;
import static io.sphere.sdk.utils.MapUtils.mapOf;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableMap;

public class HttpHeaders extends Base {
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    private final Map<String, List<String>> headers;

    private HttpHeaders(final Map<String, List<String>> headers) {
        this.headers = unmodifiableMap(headers);
    }

    private HttpHeaders(final String key, final String value) {
        this(mapOf(key, asList(value)));
    }

    public static HttpHeaders of(final Map<String, List<String>> headers) {
        return new HttpHeaders(headers);
    }

    public static HttpHeaders of(final String key, final String value) {
        return new HttpHeaders(key, value);
    }

    public static HttpHeaders of() {
        return new HttpHeaders(Collections.emptyMap());
    }

    public Optional<List<String>> getFlatHeader(final String key) {
        return Optional.ofNullable(headers.get(key));
    }

    public Map<String, List<String>> getHeadersAsMap() {
        return headers;
    }

    public HttpHeaders plus(final String key, final String value) {
        final Map<String, List<String>> copy = copyOf(headers);
        final List<String> values = copy.get(key);
        if (values == null){
            copy.put(key, asList(value));
        } else {
            values.add(value);
            copy.put(key, values);
        }

        return new HttpHeaders(copy);
    }

    @Override
    public final String toString() {
        final Map<String, List<String>> map = MapUtils.copyOf(headers);
        if (map.containsKey(AUTHORIZATION)) {
            map.put(AUTHORIZATION, asList("**removed from output**"));
        }
        return map.toString();
    }
}