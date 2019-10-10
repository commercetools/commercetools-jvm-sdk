package io.sphere.sdk.http;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

public final class HttpHeaders extends Base {
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String AUTHORIZATION = "Authorization";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_LENGTH = "Content-Length";
    public static final String X_CORRELATION_ID = "X-Correlation-ID";

    private final List<NameValuePair> data;

    private HttpHeaders(final List<NameValuePair> data) {
        this.data = unmodifiableList(data);
    }

    public static HttpHeaders of(final List<NameValuePair> headers) {
        return new HttpHeaders(headers);
    }

    public static HttpHeaders of(final Map<String, List<String>> headers) {
        return new HttpHeaders(NameValuePair.convertStringListMapToList(headers));
    }

    public static HttpHeaders of(final String key, final String value) {
        final NameValuePair nameValuePair = NameValuePair.of(key, value);
        return of(nameValuePair);
    }

    private static HttpHeaders of(final NameValuePair nameValuePair) {
        return new HttpHeaders(Collections.singletonList(nameValuePair));
    }

    public static HttpHeaders empty() {
        return of();
    }

    public static HttpHeaders of() {
        return new HttpHeaders(emptyList());
    }

    /**
     * Gets the header as list since they can occur multiple times. If it does exist, the list is empty.
     * @param key the key of the header to find
     * @return empty or filled list of header values
     */
    public List<String> getHeader(final String key) {
        return data.stream()
                .filter(entry -> entry.getName().equalsIgnoreCase(key))
                .map(entry -> entry.getValue())
                .collect(toList());
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
        return NameValuePair.convertToStringListMap(data);
    }

    public HttpHeaders plus(final String key, final String value) {
        final List<NameValuePair> list = new ArrayList<>(data.size() + 1);
        list.addAll(data);
        list.add(NameValuePair.of(key, value));
        return HttpHeaders.of(list);
    }

    @Override
    public final String toString() {
        final Map<String, List<String>> newMap = new HashMap<>();
        newMap.putAll(getHeadersAsMap());
        if (newMap.containsKey(AUTHORIZATION)) {
            newMap.put(AUTHORIZATION, Collections.singletonList("**removed from output**"));
        }
        return newMap.toString();
    }

    public static HttpHeaders ofMapEntryList(final List<Map.Entry<String, String>> entries) {
        final List<NameValuePair> nameValuePairList = entries.stream()
                .map(element -> NameValuePair.of(element.getKey(), element.getValue()))
                .collect(toList());
        return HttpHeaders.of(nameValuePairList);
    }
}