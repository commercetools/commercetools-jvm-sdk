package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.queries.MetaModelGetDsl;

import java.util.List;
import java.util.function.Function;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
public interface CustomObjectByKeyGet<T> extends MetaModelGetDsl<CustomObject<T>, CustomObject<T>, CustomObjectByKeyGet<T>, CustomObjectExpansionModel<CustomObject<T>>> {
    static <T> CustomObjectByKeyGet<T> of(final String container, final String key, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectByKeyGetImpl<>(typeReference, container, key);
    }

    static CustomObjectByKeyGet<JsonNode> of(final String container, final String key) {
        final TypeReference<CustomObject<JsonNode>> typeReference = new TypeReference<CustomObject<JsonNode>>() {
            @Override
            public String toString() {
                return "TypeReference<CustomObject<JsonNode>>";
            }
        };
        return of(container, key, typeReference);
    }

    @Override
    CustomObjectByKeyGet<T> plusExpansionPaths(final Function<CustomObjectExpansionModel<CustomObject<T>>, ExpansionPath<CustomObject<T>>> m);

    @Override
    CustomObjectByKeyGet<T> withExpansionPaths(final Function<CustomObjectExpansionModel<CustomObject<T>>, ExpansionPath<CustomObject<T>>> m);

    @Override
    CustomObjectByKeyGet<T> plusExpansionPaths(final ExpansionPath<CustomObject<T>> expansionPath);

    @Override
    CustomObjectByKeyGet<T> withExpansionPaths(final ExpansionPath<CustomObject<T>> expansionPath);

    @Override
    CustomObjectByKeyGet<T> withExpansionPaths(final List<ExpansionPath<CustomObject<T>>> expansionPaths);
}