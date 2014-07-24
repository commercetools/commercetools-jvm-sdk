package io.sphere.sdk.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.JsonUtils;

public abstract class CommandImpl<I> extends Base implements Command<I> {
    @Override
    public Function<HttpResponse, I> resultMapper() {
        return httpResponse -> JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody());
    }

    protected abstract TypeReference<I> typeReference();
}
