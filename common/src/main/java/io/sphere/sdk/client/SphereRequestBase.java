package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.exceptions.JsonException;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.JsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * A base class with utility methods for the implementation of {@link io.sphere.sdk.client.SphereRequest}s.
 */
public abstract class SphereRequestBase extends Base {
    //do not add field members here

    protected SphereRequestBase() {
    }

    protected static <T> Function<HttpResponse, T> resultMapperOf(TypeReference<T> typeReference) {
        return httpResponse -> JsonUtils.readObject(typeReference, httpResponse.getResponseBody().orElseThrow(() -> new JsonException(httpResponse)));
    }

    protected static String getBodyAsString(final HttpResponse httpResponse) {
        return new String(httpResponse.getResponseBody().get(), StandardCharsets.UTF_8);
    }
}
