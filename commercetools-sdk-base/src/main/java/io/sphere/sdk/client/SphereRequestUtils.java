package io.sphere.sdk.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public final class SphereRequestUtils {
    private SphereRequestUtils() {
    }

    public static <T> T deserialize(final HttpResponse httpResponse, final TypeReference<T> typeReference) {
        return SphereJsonUtils.readObject(Optional.ofNullable(httpResponse.getResponseBody()).orElseThrow(() -> new JsonException(httpResponse)), typeReference);
    }

    public static <T> T deserialize(final HttpResponse httpResponse, final JavaType javaType) {
        return SphereJsonUtils.readObject(Optional.ofNullable(httpResponse.getResponseBody()).orElseThrow(() -> new JsonException(httpResponse)), javaType);
    }

    public static String getBodyAsString(final HttpResponse httpResponse) {
        return new String(httpResponse.getResponseBody(), StandardCharsets.UTF_8);
    }
}
