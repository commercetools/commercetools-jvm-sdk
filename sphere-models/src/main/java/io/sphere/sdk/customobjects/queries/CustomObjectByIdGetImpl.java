package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;

import java.util.Optional;

final class CustomObjectByIdGetImpl<T> extends CustomObjectCustomJsonMappingByXGet<T> implements CustomObjectByIdGet<T> {
    private final JavaType javaType;

    public CustomObjectByIdGetImpl(final String id, final JavaType valueJavaType) {
        super("/" + id);
        this.javaType = CustomObjectUtils.getCustomObjectJavaTypeForValue(valueJavaType);
    }

    public CustomObjectByIdGetImpl(final String id, final Class<T> valueClass) {
        this(id, SphereJsonUtils.convertToJavaType(valueClass));
    }

    public CustomObjectByIdGetImpl(final String id, final TypeReference<T> valueTypeReference) {
        this(id, SphereJsonUtils.convertToJavaType(valueTypeReference));
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
