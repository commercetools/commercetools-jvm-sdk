package de.commercetools.sphere.client;

import de.commercetools.internal.ListenableFutureAdapter;
import de.commercetools.internal.RequestHolder;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.ning.http.client.AsyncCompletionHandler;

/** Request builder that does no requests to the server and just returns a prepared response. */
public class MockRequestHolder<T> implements RequestHolder<T> {
    private String url;
    private String method;
    private Multimap<String, String> queryParams = HashMultimap.create();
    private String requestBody;

    private int statusCode;
    private String responseBody;

    public MockRequestHolder(String url, String method, int statusCode, String responseBody) {
        this.url = url;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    /** Returns query parameters for assertion purposes. */
    public Multimap<String, String> getQueryParams() {
        return queryParams;
    }

    /** Simulate a request to a server - just return prepared response. */
    public ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception {
        return new ListenableFutureAdapter<T>(
                MockListenableFuture.completed(onResponse.onCompleted(new MockHttpResponse(statusCode, responseBody))));
    }

    /** Adds the parameters to an map for assertion purposes. */
    public MockRequestHolder<T> addQueryParameter(String name, String value) {
        queryParams.put(name, value);
        return this;
    }

    /** Adds the parameters to an map for assertion purposes. */
    public MockRequestHolder<T> setBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    /** Request body, for assertion purposes. */
    public String getBody() {
        return this.requestBody;
    }

    /** The URL where the request would be sent to, for assertion purposes. */
    public String getRawUrl() {
        return url;
    }

    /** The HTTP method (GET, POST), for assertion purposes. */
    public String getMethod() {
        return method;
    }
}
