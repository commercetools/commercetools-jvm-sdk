package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import java.util.Optional;

final class CustomObjectByIdGetImpl<T> extends MetaModelGetDslImpl<CustomObject<T>, CustomObject<T>, CustomObjectByIdGet<T>, CustomObjectExpansionModel<CustomObject<T>>> implements CustomObjectByIdGet<T> {
    private JavaType javaType;

    CustomObjectByIdGetImpl(final String id) {
        super(id, JsonEndpoint.of(new TypeReference<CustomObject<T>>() {
            @Override
            public String toString() {
                return "TypeReference<CustomObject<T>>";
            }
        }, "/custom-objects"), CustomObjectExpansionModel.<T>of(), CustomObjectByIdGetImpl::new);
    }

    CustomObjectByIdGetImpl(final MetaModelGetDslBuilder<CustomObject<T>, CustomObject<T>, CustomObjectByIdGet<T>, CustomObjectExpansionModel<CustomObject<T>>> builder){
        super(builder);
    }

    @Override
    public CustomObject<T> deserialize(final HttpResponse httpResponse) {
        return deserializeCustomObject(httpResponse);
    }

    protected CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse) {

        JavaType customObjectJavaType = SphereJsonUtils.createCustomObjectJavaType(CustomObject.class, javaType.getRawClass());

        return Optional.ofNullable(httpResponse)
                .filter(response -> response.getResponseBody() != null && response.getResponseBody().length > 0)
                .map(response -> response.getResponseBody())
                .map(responseBody -> SphereJsonUtils.<CustomObject<T>>readObject(httpResponse.getResponseBody(), customObjectJavaType))
                .orElse(null);
    }

    public void setJavaType(final JavaType valueJavaType){
        this.javaType = CustomObjectUtils.getCustomObjectJavaTypeForValue(valueJavaType);
    }

    public void setJavaType(final Class<T> valueClass){
        this.javaType = SphereJsonUtils.convertToJavaType(valueClass);
    }

    public void setJavaType(final TypeReference<T> valueTypeReference){
        this.javaType = SphereJsonUtils.convertToJavaType(valueTypeReference);
    }
}
