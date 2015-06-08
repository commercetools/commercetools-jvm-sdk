package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.MetaModelFetchDsl;

import java.util.List;
import java.util.function.Function;

/**
 * {@link io.sphere.sdk.client.SphereRequest} to fetch one {@link io.sphere.sdk.customobjects.CustomObject} by container and key.
 * @param <T> The type of the value of the custom object.
 */
public interface CustomObjectByKeyFetch<T> extends MetaModelFetchDsl<CustomObject<T>, CustomObjectByKeyFetch<T>, Void> {
    static <T> CustomObjectByKeyFetch<T> of(final String container, final String key, final TypeReference<CustomObject<T>> typeReference) {
        return new CustomObjectByKeyFetchImpl<>(typeReference, container, key);
    }

    static CustomObjectByKeyFetch<JsonNode> of(final String container, final String key) {
        final TypeReference<CustomObject<JsonNode>> typeReference = new TypeReference<CustomObject<JsonNode>>() {
            @Override
            public String toString() {
                return "TypeReference<CustomObject<JsonNode>>";
            }
        };
        return of(container, key, typeReference);
    }

    @Override
    CustomObjectByKeyFetch<T> plusExpansionPaths(final Function<Void, ExpansionPath<CustomObject<T>>> m);

    @Override
    CustomObjectByKeyFetch<T> withExpansionPaths(final Function<Void, ExpansionPath<CustomObject<T>>> m);

    @Override
    CustomObjectByKeyFetch<T> plusExpansionPaths(final ExpansionPath<CustomObject<T>> expansionPath);

    @Override
    CustomObjectByKeyFetch<T> withExpansionPaths(final ExpansionPath<CustomObject<T>> expansionPath);

    @Override
    CustomObjectByKeyFetch<T> withExpansionPaths(final List<ExpansionPath<CustomObject<T>>> expansionPaths);
}