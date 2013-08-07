package io.sphere.internal.request;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.DeleteRequest;
import io.sphere.internal.util.Util;
import org.codehaus.jackson.type.TypeReference;

public class DeleteRequestImpl<T> implements DeleteRequest<T>{
    final private RequestHolder<T> requestHolder;
    final private TypeReference<T> jsonParserTypeRef;

    public DeleteRequestImpl(RequestHolder<T> holder, TypeReference<T> jsonParserTypeRef) {
        requestHolder = holder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    @Override
    public Optional<T> execute() {
        return Util.sync(executeAsync());
    }

    @Override
    public ListenableFuture<Optional<T>> executeAsync() {
        return RequestExecutor.executeAndHandleError(requestHolder, 404, jsonParserTypeRef);
    }
}
