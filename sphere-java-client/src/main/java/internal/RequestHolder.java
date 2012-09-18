package de.commercetools.internal;

import com.ning.http.client.AsyncCompletionHandler;
import com.google.common.util.concurrent.ListenableFuture;

/** Abstraction over HTTP request execution. Allows for mocking in tests.
 *  The default implementation is {@link RequestHolderImpl}. */
public interface RequestHolder<T> {
    /** Adds a parameter to the request query string. */
    RequestHolder<T> addQueryParameter(String name, String value);

    /** Sets a body for this request. */
    RequestHolder<T> setBody(String requestBody);

    /** Executes a request to a server. */
    ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception;

    /** The URL the request will be sent to, for debugging purposes. */
    String getRawUrl();

    /** The body of the request, for debugging purposes. */
    String getBody();
}
