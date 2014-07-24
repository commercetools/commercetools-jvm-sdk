package io.sphere.sdk.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import java.util.Optional;
import io.sphere.sdk.utils.JsonUtils;

public abstract class FetchImpl<T> implements Fetch<T> {
    @Override
    public Function<HttpResponse, Optional<T>> resultMapper() {
        return httpResponse -> {
            final Optional<T> result;
            if (httpResponse.getStatusCode() == 404) {
                result = Optional.empty();
            } else {
                result = Optional.of(JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody()));
            }
            return result;
        };
    }

    protected abstract TypeReference<T> typeReference();
}
