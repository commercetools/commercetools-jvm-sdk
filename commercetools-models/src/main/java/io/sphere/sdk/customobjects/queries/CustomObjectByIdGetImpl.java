package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import java.util.Optional;

final class CustomObjectByIdGetImpl<T> extends MetaModelGetDslImpl<CustomObject<T>, CustomObject<T>, CustomObjectByIdGet<T>, CustomObjectExpansionModel<CustomObject<T>>> implements CustomObjectByIdGet<T> {
    private final JavaType javaType;

     CustomObjectByIdGetImpl(final String id, final JavaType valueJavaType) {
        super(id, JsonEndpoint.of(new TypeReference<CustomObject<T>>() {
            @Override
            public String toString() {
                return "TypeReference<CustomObject<T>>";
            }
        }, "/types"), CustomObjectExpansionModel.<T>of(), null);

        this.javaType = CustomObjectUtils.getCustomObjectJavaTypeForValue(valueJavaType);
    }

    CustomObjectByIdGetImpl(final String id, final Class<T> valueClass) {
        this(id, SphereJsonUtils.convertToJavaType(valueClass));
    }

    CustomObjectByIdGetImpl(final String id, final TypeReference<T> valueTypeReference) {
        this(id, SphereJsonUtils.convertToJavaType(valueTypeReference));
    }

    protected CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse) {
        return Optional.ofNullable(httpResponse)
                .filter(response -> response.getResponseBody() != null && response.getResponseBody().length > 0)
                .map(response -> response.getResponseBody())
                .map(responseBody -> SphereJsonUtils.<CustomObject<T>>readObject(httpResponse.getResponseBody(), javaType))
                .orElse(null);
    }
}
