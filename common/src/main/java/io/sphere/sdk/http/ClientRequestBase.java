package io.sphere.sdk.http;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.EmptyHttpBodyException;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.JsonUtils;

import java.util.function.Function;

public abstract class ClientRequestBase extends Base {
    protected static <T> Function<HttpResponse, T> resultMapperOf(TypeReference<T> typeReference) {
        return httpResponse -> JsonUtils.readObject(typeReference, httpResponse.getResponseBody().orElseThrow(() -> new EmptyHttpBodyException(httpResponse)));
    }
}
