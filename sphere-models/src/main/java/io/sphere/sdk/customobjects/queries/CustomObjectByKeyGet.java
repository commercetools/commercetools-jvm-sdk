package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.queries.Get;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
public interface CustomObjectByKeyGet<T> extends Get<CustomObject<T>> {

    /**
     * Creates an object to fetch a custom object by the container and key with POJO mapping.
     * @param container container of the custom object to fetch
     * @param key key of the custom object to fetch
     * @param typeReference the type reference of the value of the custom object
     * @param <T> the type of the value in the custom object
     * @return query object
     */
    static <T> CustomObjectByKeyGet<T> of(final String container, final String key, final TypeReference<T> typeReference) {
        return new CustomObjectByKeyGetImpl<>(container, key, typeReference);
    }

    /**
     * Creates an object to fetch a custom object by the container and get the result as {@link JsonNode}.
     * @param container container of the custom object to fetch
     * @param key key of the custom object to fetch
     * @return query object
     */
    static CustomObjectByKeyGet<JsonNode> ofJsonNode(final String container, final String key) {
        return of(container, key, TypeReferences.jsonNodeTypeReference());
    }

    /**
     * @deprecated use {@link #ofJsonNode(String, String)} instead
     */
    @Deprecated
    static CustomObjectByKeyGet<JsonNode> of(final String container, final String key) {
        return ofJsonNode(container, key);
    }
}