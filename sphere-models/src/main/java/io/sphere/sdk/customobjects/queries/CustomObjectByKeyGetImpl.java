package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelGetDslImpl;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
final class CustomObjectByKeyGetImpl<T> extends MetaModelGetDslImpl<CustomObject<T>, CustomObject<T>, CustomObjectByKeyGet<T>, CustomObjectExpansionModel<CustomObject<T>>> implements CustomObjectByKeyGet<T> {

    CustomObjectByKeyGetImpl(final TypeReference<CustomObject<T>> typeReference, final String container, final String key) {
        super("" + container + "/" + key, JsonEndpoint.of(typeReference, CustomObjectEndpoint.PATH), CustomObjectExpansionModel.<T>of(), builder -> new CustomObjectByKeyGetImpl<>(builder));
    }

    public CustomObjectByKeyGetImpl(final MetaModelFetchDslBuilder<CustomObject<T>, CustomObject<T>, CustomObjectByKeyGet<T>, CustomObjectExpansionModel<CustomObject<T>>> builder) {
        super(builder);
    }
}
