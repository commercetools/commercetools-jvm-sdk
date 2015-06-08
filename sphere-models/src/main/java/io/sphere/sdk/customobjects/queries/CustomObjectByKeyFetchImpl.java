package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.MetaModelFetchDslBuilder;
import io.sphere.sdk.queries.MetaModelFetchDslImpl;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
final class CustomObjectByKeyFetchImpl<T> extends MetaModelFetchDslImpl<CustomObject<T>, CustomObjectByKeyFetch<T>, Void> implements CustomObjectByKeyFetch<T> {

    CustomObjectByKeyFetchImpl(final TypeReference<CustomObject<T>> typeReference, final String container, final String key) {
        super("" + container + "/" + key, JsonEndpoint.of(typeReference, CustomObjectEndpoint.PATH), null, builder -> new CustomObjectByKeyFetchImpl<>(builder));
    }

    public CustomObjectByKeyFetchImpl(final MetaModelFetchDslBuilder<CustomObject<T>, CustomObjectByKeyFetch<T>, Void> builder) {
        super(builder);
    }
}
