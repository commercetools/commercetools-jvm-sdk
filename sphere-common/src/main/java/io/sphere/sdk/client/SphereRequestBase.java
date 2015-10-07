package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * A base class with utility methods for the implementation of {@link io.sphere.sdk.client.SphereRequest}s.
 */
public abstract class SphereRequestBase extends Base {
    //do not add field members here

    protected SphereRequestBase() {
    }

    protected static <T> T deserialize(final HttpResponse httpResponse, final TypeReference<T> typeReference) {
        return SphereJsonUtils.readObject(Optional.ofNullable(httpResponse.getResponseBody()).orElseThrow(() -> new JsonException(httpResponse)), typeReference);
    }

    protected static <T> T deserialize(final HttpResponse httpResponse, final JavaType javaType) {
        return SphereJsonUtils.readObject(Optional.ofNullable(httpResponse.getResponseBody()).orElseThrow(() -> new JsonException(httpResponse)), javaType);
    }

    protected static String getBodyAsString(final HttpResponse httpResponse) {
        return new String(httpResponse.getResponseBody(), StandardCharsets.UTF_8);
    }
}
