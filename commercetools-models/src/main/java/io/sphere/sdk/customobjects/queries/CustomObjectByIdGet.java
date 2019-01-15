package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.queries.MetaModelGetDsl;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link CustomObject} by id.
 *
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectByIdGetIntegrationTest#execution()}
 *
 * @param <T> The type of the value of the custom object.
 * @see CustomObject
 */
public interface CustomObjectByIdGet<T> extends MetaModelGetDsl<CustomObject<T>, CustomObject<T>, CustomObjectByIdGet<T>, CustomObjectExpansionModel<CustomObject<T>>> {

    /**
     * Creates an object to fetch a custom object by id with POJO mapping.
     * @param id id of the custom object to fetch
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T> the type of the value in the custom object
     * @return query object
     */
    static <T> CustomObjectByIdGet<T> of(final String id, final Class<T> valueClass) {
        CustomObjectByIdGetImpl<T> customObjectByIdGetImpl = new CustomObjectByIdGetImpl<>(id);
        customObjectByIdGetImpl.setJavaType(valueClass);
        return customObjectByIdGetImpl;
    }

    /**
     * Creates an object to fetch a custom object by the container and key with POJO mapping.
     * @param id id of the custom object to fetch
     * @param valueTypeReference the type reference of the value of the custom object
     * @param <T> the type of the value in the custom object
     * @return query object
     */
    static <T> CustomObjectByIdGet<T> of(final String id, final TypeReference<T> valueTypeReference) {
        CustomObjectByIdGetImpl<T> customObjectByIdGetImpl = new CustomObjectByIdGetImpl<>(id);
        customObjectByIdGetImpl.setJavaType(valueTypeReference);
        return customObjectByIdGetImpl;
    }

    /**
     * Creates an object to fetch a custom object by the container and get the result as {@link JsonNode}.
     * @param id id of the custom object to fetch
     * @return query object
     */
    static CustomObjectByIdGet<JsonNode> ofJsonNode(final String id) {
        return of(id, TypeReferences.jsonNodeTypeReference());
    }
}