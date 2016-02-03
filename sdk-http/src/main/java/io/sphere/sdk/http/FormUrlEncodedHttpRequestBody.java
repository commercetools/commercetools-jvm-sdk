package io.sphere.sdk.http;

import java.util.*;

import static java.util.Collections.unmodifiableList;

public final class FormUrlEncodedHttpRequestBody extends Base implements HttpRequestBody {
    private final List<NameValuePair> data;

    private FormUrlEncodedHttpRequestBody(final List<NameValuePair> data) {
        this.data = unmodifiableList(new ArrayList<>(data));
    }

    public static FormUrlEncodedHttpRequestBody of(final List<NameValuePair> data) {
        return new FormUrlEncodedHttpRequestBody(data);
    }

    /**
     * Creator if the order does not matter and there are no duplicates.
     * @param data name value pairs for the body
     * @return FormUrlEncodedHttpRequestBody
     */
    public static FormUrlEncodedHttpRequestBody of(final Map<String, String> data) {
        final List<NameValuePair> list = new ArrayList<>(data.size());
        data.forEach((key, value) -> list.add(NameValuePair.of(key, value)));
        return of(list);
    }

    /**
     * @return the unordered data without duplicate names
     * @deprecated use {@link #getParameters()} instead
     */
    @Deprecated
    public Map<String, String> getData() {
        final Map<String, String> map = new HashMap<>();
        getParameters().forEach(pair -> map.put(pair.getName(), pair.getValue()));
        return map;
    }

    public List<NameValuePair> getParameters() {
        return data;
    }
}