package io.sphere.client;

import com.google.common.base.Strings;
import io.sphere.internal.ListenableFutureAdapter;
import io.sphere.internal.request.RequestHolder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ning.http.client.AsyncCompletionHandler;
import io.sphere.internal.request.SphereResultRaw;
import io.sphere.internal.util.QueryStringConstruction;
import io.sphere.internal.util.Util;

/** Request that just returns a prepared response instead of doing real HTTP communication. */
public class MockRequestHolder<T> implements RequestHolder<T> {
    private String baseUrl;
    private String method;
    private Multimap<String, String> queryParams = HashMultimap.create();
    private String requestBody;

    private int statusCode;
    private String responseBody;

    public MockRequestHolder(String baseUrl, String method, int statusCode, String responseBody) {
        this.baseUrl = baseUrl;
        this.method = method;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /** Simulate a request to a server - just return prepared response. */
    public ListenableFuture<SphereResultRaw<T>> executeRequest(AsyncCompletionHandler<SphereResultRaw<T>> onResponse) throws Exception {
        return new ListenableFutureAdapter<SphereResultRaw<T>>(
                MockListenableFuture.completed(onResponse.onCompleted(new MockHttpResponse(statusCode, responseBody))));
    }

    /** Remembers the query parameter, for test assertions. */
    public MockRequestHolder<T> addQueryParameter(String name, String value) {
        queryParams.put(name, value);
        return this;
    }

    /** Remembers request body, for assertion purposes. */
    public MockRequestHolder<T> setBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    /** The HTTP method (GET, POST), for test assertions. */
    @Override public String getMethod() { return method; }

    /** Request body, for assertion purposes. */
    @Override public String getBody() { return requestBody; }

    /** The URL where the request would be sent to, for assertion purposes. */
    @Override public String getUrl() {
        return addQueryString(baseUrl, queryParams);
    }

    @Override public String toString() {
        return Util.debugPrintRequestHolder(this);
    }

    // --------------
    // Helpers
    // --------------

    /** Adds query params to bare base url, e.g. http://example.com */
    private static String addQueryString(String baseUrl, Multimap<String,String> queryParams) {
        String queryString = QueryStringConstruction.toQueryString(queryParams);
        String joiner = baseUrl.contains("?") ? "&" : "?";
        return !Strings.isNullOrEmpty(queryString) ? baseUrl + joiner + queryString : baseUrl;
    }
}
