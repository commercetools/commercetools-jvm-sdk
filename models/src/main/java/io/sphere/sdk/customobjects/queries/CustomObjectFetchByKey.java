package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.queries.FetchImpl;

public class CustomObjectFetchByKey<T> extends FetchImpl<CustomObject<T>> {

    private CustomObjectFetchByKey(final TypeReference<CustomObject<T>> typeReference, final String container, final String key) {
        super(JsonEndpoint.of(typeReference, CustomObjectsEndpoint.PATH), "" + container + "/" + key);
    }

    public static <T> CustomObjectFetchByKey<T> of(final String container, final String key, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectFetchByKey<>(typeReference, container, key);
    }
}
