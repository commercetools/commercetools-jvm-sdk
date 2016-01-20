package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

final class CustomObjectQueryImpl<T> extends MetaModelQueryDslImpl<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> implements CustomObjectQuery<T> {
    CustomObjectQueryImpl(final TypeReference<T> valueTypeReference) {
        this(SphereJsonUtils.convertToJavaType(valueTypeReference));
    }

    CustomObjectQueryImpl(final Class<T> valueClass) {
        this(SphereJsonUtils.convertToJavaType(valueClass));
    }

    CustomObjectQueryImpl(final JavaType valueJavaType) {
        super(CustomObjectEndpoint.PATH, CustomObjectUtils.getCustomObjectJavaTypeForValue(valueJavaType), CustomObjectQueryModel.of(), CustomObjectExpansionModel.<T>of(), b -> new CustomObjectQueryImpl<>(b));
    }

    private CustomObjectQueryImpl(final MetaModelQueryDslBuilder<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> builder) {
        super(builder);
    }
}