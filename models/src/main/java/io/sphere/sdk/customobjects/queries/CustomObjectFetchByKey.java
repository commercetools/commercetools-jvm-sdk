package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.queries.FetchImpl;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
public class CustomObjectFetchByKey<T> extends FetchImpl<CustomObject<T>> {

    private static final TypeReference<CustomObject<JsonNode>> JSON_NODE_TYPE_REFERENCE = new TypeReference<CustomObject<JsonNode>>() {
        @Override
        public String toString() {
            return "TypeReference<CustomObject<JsonNode>>";
        }
    };

    private CustomObjectFetchByKey(final TypeReference<CustomObject<T>> typeReference, final String container, final String key) {
        super(JsonEndpoint.of(typeReference, CustomObjectsEndpoint.PATH), "" + container + "/" + key);
    }

    public static <T> CustomObjectFetchByKey<T> of(final String container, final String key, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectFetchByKey<>(typeReference, container, key);
    }

    public static CustomObjectFetchByKey<JsonNode> of(final String container, final String key) {
        return of(container, key, JSON_NODE_TYPE_REFERENCE);
    }
}
