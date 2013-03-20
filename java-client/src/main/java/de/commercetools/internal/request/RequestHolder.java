package de.commercetools.internal.request;

import com.ning.http.client.AsyncCompletionHandler;
import com.google.common.util.concurrent.ListenableFuture;

/** Abstraction over HTTP request execution. Allows for mocking in tests. */
// This interface is split into two interfaces because it logically has two parts:
// Tests only care about reading the TestableRequestHolder.
public interface RequestHolder<T> extends TestableRequestHolder {
    /** Adds a parameter to the request query string. */
    RequestHolder<T> addQueryParameter(String name, String value);

    /** Sets a body for this request. */
    RequestHolder<T> setBody(String requestBody);

    /** Executes a request to a server. */
    ListenableFuture<T> executeRequest(AsyncCompletionHandler<T> onResponse) throws Exception;
}
