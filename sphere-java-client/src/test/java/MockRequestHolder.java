package de.commercetools.sphere.client;

import de.commercetools.internal.ListenableFutureAdapter;
import de.commercetools.internal.request.RequestHolder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ning.http.client.AsyncCompletionHandler;
import de.commercetools.internal.util.QueryStringConstruction;
import de.commercetools.internal.util.Util;

/** Request that does no requests to the server and just returns a prepared response. */
public class MockRequestHolder<T> implements RequestHolder<T> {
    private String url;
    private String method;
    private Multimap<String, String> queryParams = HashMultimap.create();
    private String requestBody;

    private int statusCode;
    private String responseBody;

    public MockRequestHolder(String url, String method, int statusCode, String responseBody) {
        this.url = url;
        this.method = method;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /** Simulate a request to a server - just return prepared response. */
    public ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception {
        return new ListenableFutureAdapter<T>(
                MockListenableFuture.completed(onResponse.onCompleted(new MockHttpResponse(statusCode, responseBody))));
    }

    /** Remembers the query parameter, for test assertions. */
    public MockRequestHolder<T> addQueryParameter(String name, String value) {
        queryParams.put(name, value);
        return this;
    }

    /** Remembers the body, for test assertions. */
    public MockRequestHolder<T> setBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    /** Returns query parameters for assertion purposes. */
    @Override public Multimap<String, String> getQueryParams() { return queryParams; }

    /** The HTTP method (GET, POST), for test assertions. */
    @Override public String getMethod() { return method; }

    /** The URL where the request would be sent to, for test assertions. */
    @Override public String getUrl() { return url; }

    @Override public String getUrlWithQueryParams() {
        return getUrl() + QueryStringConstruction.toQueryString(getQueryParams());
    }

    /** Request body, for test assertions. */
    @Override public String getBody() { return requestBody; }

    @Override public String toString() {
        return Util.debugPrintRequestHolder(this);
    }
}
