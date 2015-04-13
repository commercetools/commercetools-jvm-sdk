package io.sphere.sdk.http;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.ImmutableMapBuilder;

import java.util.*;

import static io.sphere.sdk.utils.ListUtils.*;
import static io.sphere.sdk.utils.MapUtils.*;
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

    public static HttpHeaders empty() {
        return of();
    }

    public static HttpHeaders of() {
        return new HttpHeaders(Collections.emptyMap());
    }

    /**
     * Gets the header as list since they can occur multiple times. If it does exist, the list is empty.
     * @param key the key of the header to find
     * @return empty or filled list of header values
     */
    public List<String> getHeader(final String key) {
        return Optional.ofNullable(headers.get(key)).orElse(Collections.emptyList());
    }

    /**
     * Finds the first header value for a certain key.
     * @param key the key of the header to find
     * @return the header value as optional
     */
    public Optional<String> getFlatHeader(final String key) {
        return getHeader(key).stream().findFirst();
    }

    public Map<String, List<String>> getHeadersAsMap() {
        return headers;
    }

    public HttpHeaders plus(final String key, final String value) {
        final List<String> currentValues = headers.getOrDefault(key, Collections.emptyList());
        final List<String> newValues = listOf(currentValues, value);
        final Map<String, List<String>> newMap = ImmutableMapBuilder.<String, List<String>>of()
                .putAll(headers)
                .put(key, newValues)
                .build();
        return HttpHeaders.of(newMap);
    }

    @Override
    public final String toString() {
        final Map<String, List<String>> map = copyOf(headers);
        if (map.containsKey(AUTHORIZATION)) {
            map.put(AUTHORIZATION, asList("**removed from output**"));
        }
        return map.toString();
    }
}