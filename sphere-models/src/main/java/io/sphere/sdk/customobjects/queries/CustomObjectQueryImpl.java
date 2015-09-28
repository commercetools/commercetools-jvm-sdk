package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.queries.MetaModelQueryDslBuilder;
import io.sphere.sdk.queries.MetaModelQueryDslImpl;

public class CustomObjectQueryImpl<T> extends MetaModelQueryDslImpl<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> implements CustomObjectQuery<T> {
    CustomObjectQueryImpl(final TypeReference<T> valueTypeReference) {
        super(CustomObjectEndpoint.PATH, CustomObjectUtils.getCustomObjectJavaTypeForValue(valueTypeReference), CustomObjectQueryModel.of(), CustomObjectExpansionModel.<T>of(), b -> new CustomObjectQueryImpl<T>(b));
    }

    private CustomObjectQueryImpl(final MetaModelQueryDslBuilder<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> builder) {
        super(builder);
    }
}