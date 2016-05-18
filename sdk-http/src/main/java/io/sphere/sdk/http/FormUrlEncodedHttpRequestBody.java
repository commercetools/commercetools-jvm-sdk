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
    public static FormUrlEncodedHttpRequestBody ofStringMap(final Map<String, String> data) {
        return of(NameValuePair.convertStringMapToList(data));
    }

    /**
     * Creator if the order does not matter and there are no duplicates.
     * @param data name value pairs for the body
     * @return FormUrlEncodedHttpRequestBody
     */
    public static FormUrlEncodedHttpRequestBody of(final Map<String, List<String>> data) {
        return of(NameValuePair.convertStringListMapToList(data));
    }

    public List<NameValuePair> getParameters() {
        return data;
    }
}