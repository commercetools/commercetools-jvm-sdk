package io.sphere.sdk.http;

import java.util.*;

import static java.util.Collections.unmodifiableMap;

public final class HttpHeaders extends Base {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    private final Map<String, List<String>> headers;

    private HttpHeaders(final Map<String, List<String>> headers) {
        this.headers = unmodifiableMap(headers);
    }

    public static HttpHeaders of(final Map<String, List<String>> headers) {
        return new HttpHeaders(headers);
    }

    public static HttpHeaders of(final String key, final String value) {
        final Map<String, List<String>> headers = new HashMap<>();
        headers.put(key, Collections.singletonList(value));
        return new HttpHeaders(headers);
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
    public Optional<String> findFlatHeader(final String key) {
        return getHeader(key).stream().findFirst();
    }

    public Map<String, List<String>> getHeadersAsMap() {
        return headers;
    }

    public HttpHeaders plus(final String key, final String value) {
        final List<String> currentValues = headers.getOrDefault(key, Collections.emptyList());
        final List<String> newValues = new ArrayList<>(currentValues.size() + 1);
        newValues.addAll(currentValues);
        newValues.add(value);

        final Map<String, List<String>> newMap = new HashMap<>();
        newMap.putAll(headers);
        newMap.put(key, newValues);
        return HttpHeaders.of(newMap);
    }

    @Override
    public final String toString() {
        final Map<String, List<String>> newMap = new HashMap<>();
        newMap.putAll(headers);
        if (newMap.containsKey(AUTHORIZATION)) {
            newMap.put(AUTHORIZATION, Collections.singletonList("**removed from output**"));
        }
        return newMap.toString();
    }
}