package de.commercetools.internal.request;

import com.google.common.util.concurrent.ListenableFuture;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncCompletionHandler;
import de.commercetools.internal.ListenableFutureAdapter;

/** A request holder that does real HTTP requests. Can be mocked in tests. */
public class RequestHolderImpl<T> implements RequestHolder<T> {
    private final AsyncHttpClient.BoundRequestBuilder httpRequestBuilder;

    public RequestHolderImpl(AsyncHttpClient.BoundRequestBuilder httpRequestBuilder) {
        this.httpRequestBuilder = httpRequestBuilder;
    }

    public RequestHolderImpl<T> addQueryParameter(String name, String value) {
        httpRequestBuilder.addQueryParameter(name, value);
        return this;
    }

    public RequestHolderImpl<T> setBody(String requestBody) {
        httpRequestBuilder.setBody(requestBody);
        return this;
    }

    public ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception {
        // make a request to the backend
        return new ListenableFutureAdapter<T>(httpRequestBuilder.execute(onResponse));
    }

    /** The URL the request will be sent to, for debugging purposes. */
    public String getRawUrl() {
        return httpRequestBuilder.build().getRawUrl();
    }

    /** The HTTP method of the request, for debugging purposes. */
    public String getMethod() {
        return httpRequestBuilder.build().getMethod();
    }

    /** The body of the request, for debugging purposes. */
    public String getBody() {
        return httpRequestBuilder.build().getStringData();
    }
}