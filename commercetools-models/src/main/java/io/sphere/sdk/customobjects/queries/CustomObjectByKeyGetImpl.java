package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Optional;

final class CustomObjectByKeyGetImpl<T> extends CustomObjectCustomJsonMappingByKeyGet<T> implements CustomObjectByKeyGet<T> {
    private final JavaType javaType;

    public CustomObjectByKeyGetImpl(final String container, final String key, final JavaType javaType) {
        super(container, key);
        this.javaType = CustomObjectUtils.getCustomObjectJavaTypeForValue(javaType);
    }

    public CustomObjectByKeyGetImpl(final String container, final String key, final Class<T> clazz) {
        this(container, key, SphereJsonUtils.convertToJavaType(clazz));
    }

    public CustomObjectByKeyGetImpl(final String container, final String key, final TypeReference<T> typeReference) {
        this(container, key, SphereJsonUtils.convertToJavaType(typeReference));
    }

    @Override
    protected CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse)
                .filter(response -> response.getResponseBody() != null && response.getResponseBody().length > 0)
                .map(response -> response.getResponseBody())
                .map(responseBody -> SphereJsonUtils.<CustomObject<T>>readObject(httpResponse.getResponseBody(), javaType))
                .orElse(null);
    }
}
