package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Optional;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
final class CustomObjectByKeyGetImpl<T> extends CustomObjectCustomJsonMappingByKeyGet<T> implements CustomObjectByKeyGet<T> {
    private final TypeReference<T> typeReference;

    public CustomObjectByKeyGetImpl(final String container, final String key, final TypeReference<T> typeReference) {
        super(container, key);
        this.typeReference = typeReference;
    }

    @Override
    protected CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse)
                .filter(response -> response.getResponseBody() != null && response.getResponseBody().length > 0)
                .map(response -> response.getResponseBody())
                .map(responseBody -> {
                    final JavaType resultJavaType = CustomObjectUtils.getCustomObjectJavaTypeForValue(typeReference);
                    return SphereJsonUtils.<CustomObject<T>>readObject(httpResponse.getResponseBody(), resultJavaType);
                })
                .orElse(null);
    }
}
