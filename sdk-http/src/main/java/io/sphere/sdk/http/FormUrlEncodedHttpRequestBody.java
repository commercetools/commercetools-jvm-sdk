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
        return of(NameValuePair.convertStringMapToList(data));
    }

    /**
     * @return the unordered data without duplicate names
     * @deprecated use {@link #getParameters()} instead
     */
    @Deprecated
    public Map<String, String> getData() {
        return NameValuePair.convertToStringMap(getParameters());
    }

    public List<NameValuePair> getParameters() {
        return data;
    }
}