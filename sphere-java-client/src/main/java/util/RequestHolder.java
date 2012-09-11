package de.commercetools.sphere.client.util;

import com.ning.http.client.AsyncCompletionHandler;
import com.ning.http.client.ListenableFuture;

/** Abstraction over HTTP request execution. Allows for mocking in tests.
 * The default implementation is {@link RequestHolderImpl}. */
public interface RequestHolder<T> {
    /** Adds a parameter to the request query string. */
    void addQueryParameter(String name, String value);

    /** The URL the request will be sent to. */
    String getRawUrl();

    /** Executes a request to a server. */
    ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception;
}
