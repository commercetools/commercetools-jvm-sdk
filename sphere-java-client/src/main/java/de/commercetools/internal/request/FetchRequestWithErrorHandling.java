package de.commercetools.internal.request;

import com.google.common.base.Optional;
import de.commercetools.internal.Defaults;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.SphereException;

import com.google.common.util.concurrent.ListenableFuture;
import org.codehaus.jackson.type.TypeReference;

public class FetchRequestWithErrorHandling<T> implements FetchRequest<T>, TestableRequest {
    RequestHolder<T> requestHolder;
    int handledErrorStatus;
    TypeReference<T> jsonParserTypeRef;

    public FetchRequestWithErrorHandling(RequestHolder<T> requestHolder, int handledErrorStatus, TypeReference<T> jsonParserTypeRef) {
        if (requestHolder == null) throw new NullPointerException("requestHolder");
        if (jsonParserTypeRef == null) throw new NullPointerException("jsonParserTypeRef");
        this.requestHolder = requestHolder;
        this.handledErrorStatus = handledErrorStatus;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override public Optional<T> fetch() {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    @Override public ListenableFuture<Optional<T>> fetchAsync() {
        return RequestExecutor.executeAndHandleError(requestHolder, handledErrorStatus, jsonParserTypeRef);
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
