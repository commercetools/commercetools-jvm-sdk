package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.queries.MetaModelGetDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;
import java.util.Optional;

final class CustomObjectByIdGetImpl<T> extends MetaModelGetDslImpl<CustomObject<T>, CustomObject<T>, CustomObjectByIdGet<T>, CustomObjectExpansionModel<CustomObject<T>>> implements CustomObjectByIdGet<T> {

    private final JavaType customObjectJavaType;

    CustomObjectByIdGetImpl(final String id, final JavaType javaType) {
        super(id, JsonEndpoint.of(new TypeReference<CustomObject<T>>() {
            @Override
            public String toString() {
                return "TypeReference<CustomObject<T>>";
            }
        }, "/custom-objects"), CustomObjectExpansionModel.<T>of(), att -> new CustomObjectByIdGetImpl<T>(att, javaType));
        this.customObjectJavaType = SphereJsonUtils.createCustomObjectJavaType(CustomObject.class, javaType.getRawClass());
    }

    CustomObjectByIdGetImpl(final MetaModelGetDslBuilder<CustomObject<T>, CustomObject<T>, CustomObjectByIdGet<T>, CustomObjectExpansionModel<CustomObject<T>>> builder, JavaType javaType){
        super(builder);
        this.customObjectJavaType = SphereJsonUtils.createCustomObjectJavaType(CustomObject.class, javaType.getRawClass());
    }

    @Override
    public CustomObject<T> deserialize(final HttpResponse httpResponse) {
        return deserializeCustomObject(httpResponse);
    }

    protected CustomObject<T> deserializeCustomObject(final HttpResponse httpResponse) {

        if(!httpResponse.hasSuccessResponseCode()){
            return null;
        }

        return Optional.ofNullable(httpResponse)
                .filter(response -> response.getResponseBody() != null && response.getResponseBody().length > 0)
                .map(response -> response.getResponseBody())
                .map(responseBody -> SphereJsonUtils.<CustomObject<T>>readObject(httpResponse.getResponseBody(), customObjectJavaType))
                .orElse(null);
    }

}
