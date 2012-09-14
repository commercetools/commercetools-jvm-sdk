package de.commercetools.internal;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.ListenableFuture;

/** A request holder that does real HTTP requests (alternative mock implementations can only simulate requests). */
public class RequestHolderImpl<T> implements RequestHolder<T> {

    protected AsyncHttpClient.BoundRequestBuilder httpRequestBuilder;

    public RequestHolderImpl(AsyncHttpClient.BoundRequestBuilder httpRequestBuilder) {
        this.httpRequestBuilder = httpRequestBuilder;
    }

    public void addQueryParameter(String name, String value) {
        httpRequestBuilder.addQueryParameter(name, value);
    }

    public String getRawUrl() {
        return httpRequestBuilder.build().getRawUrl();
    }

    public ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception {
        // make a request to the server
        return httpRequestBuilder.execute(onResponse);
    }
}