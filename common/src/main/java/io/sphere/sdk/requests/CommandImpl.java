package io.sphere.sdk.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import io.sphere.sdk.utils.JsonUtils;

public abstract class CommandImpl<I, R extends I> implements Command<I> {
    @Override
    public Function<HttpResponse, I> resultMapper() {
        return httpResponse -> JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody());
    }

    protected abstract TypeReference<R> typeReference();
}
