package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key.
 *
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectByKeyGetIntegrationTest#execution()}
 *
 * @param <T> The type of the value of the custom object.
 * @see CustomObject
 */
public interface CustomObjectByKeyGet<T> extends MetaModelGetDsl<CustomObject<T>, CustomObject<T>, CustomObjectByKeyGet<T>, CustomObjectExpansionModel<CustomObject<T>>> {

    /**
     * Creates an object to fetch a custom object by the container and key with POJO mapping.
     * @param container container of the custom object to fetch
     * @param key key of the custom object to fetch
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T> the type of the value in the custom object
     * @return query object
     */
    static <T> CustomObjectByKeyGet<T> of(final String container, final String key, final Class<T> valueClass) {
        return new CustomObjectByKeyGetImpl<T>(container, key, SphereJsonUtils.convertToJavaType(valueClass));
    }

    /**
     * Creates an object to fetch a custom object by the container and key with POJO mapping.
     * @param container container of the custom object to fetch
     * @param key key of the custom object to fetch
     * @param valueTypeReference the type reference of the value of the custom object
     * @param <T> the type of the value in the custom object
     * @return query object
     */
    static <T> CustomObjectByKeyGet<T> of(final String container, final String key, final TypeReference<T> valueTypeReference) {
        return new CustomObjectByKeyGetImpl<T>(container, key, SphereJsonUtils.convertToJavaType(valueTypeReference));
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

    @Override
    List<ExpansionPath<CustomObject<T>>> expansionPaths();

    @Override
    CustomObjectByKeyGet<T> plusExpansionPaths(final ExpansionPath<CustomObject<T>> expansionPath);

    @Override
    CustomObjectByKeyGet<T> withExpansionPaths(final ExpansionPath<CustomObject<T>> expansionPath);

    @Override
    CustomObjectByKeyGet<T> withExpansionPaths(final List<ExpansionPath<CustomObject<T>>> expansionPaths);

}