package io.sphere.internal.request;

import com.ning.http.client.AsyncCompletionHandler;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.Result;

/** Abstraction over HTTP request execution. Allows for mocking in tests. */
// This interface is split into two interfaces because it logically has two parts:
// tests only care about TestableRequestHolder.
public interface RequestHolder<T> extends TestableRequestHolder {
    /** Adds a parameter to the request query string. */
    RequestHolder<T> addQueryParameter(String name, String value);

    /** Sets a body for this request. */
    RequestHolder<T> setBody(String requestBody);

    /** Executes a request to a server. */
    ListenableFuture<Result<T>> executeRequest(AsyncCompletionHandler<Result<T>> onResponse) throws Exception;
}
