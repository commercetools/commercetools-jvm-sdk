package io.sphere.internal.request;

import com.google.common.base.Optional;
import io.sphere.internal.util.Util;
import io.sphere.client.FetchRequest;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

public class FetchRequestImpl<T> implements FetchRequest<T>, TestableRequest {
    RequestHolder<T> requestHolder;
    TypeReference<T> jsonParserTypeRef;

    public FetchRequestImpl(RequestHolder<T> requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override public Optional<T> fetch() {
        return Util.sync(fetchAsync());
    }

    @Override public ListenableFuture<Optional<T>> fetchAsync() {
        return RequestExecutor.executeAndHandleError(requestHolder, 404, jsonParserTypeRef);
    }

    @Override public FetchRequest<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.addQueryParameter("expand", path);
        }
        return this;
    }

    // testing purposes
    @Override public TestableRequestHolder getRequestHolder() {
        return requestHolder;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return getRequestHolder().toString();
    }
}
