package io.sphere.sdk.customobjects.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.expansion.CustomObjectExpansionModel;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.queries.MetaModelQueryDsl;

/**
 * <p>Queries custom objects.</p>
 *
 *
 *
 *
 * {@include.example io.sphere.sdk.customobjects.queries.CustomObjectQueryIntegrationTest#queryByContainer()}
 *
 * @param <T> The type of the value of this custom object.
 * @see CustomObject
 */
public interface CustomObjectQuery<T> extends MetaModelQueryDsl<CustomObject<T>, CustomObjectQuery<T>, CustomObjectQueryModel<CustomObject<T>>, CustomObjectExpansionModel<CustomObject<T>>> {

    /**
     * Query object for custom objects where the result value is a POJO.
     * @param valueTypeReference the type reference of the value of the custom object
     * @param <T> the Java type of the value of the custom object
     * @return query object
     */
    static <T> CustomObjectQuery<T> of(final TypeReference<T> valueTypeReference) {
        return new CustomObjectQueryImpl<>(valueTypeReference);
    }

    /**
     * Query object for custom objects where the result value is a POJO.
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T> the Java type of the value of the custom object
     * @return query object
     */
    static <T> CustomObjectQuery<T> of(final Class<T> valueClass) {
        return new CustomObjectQueryImpl<>(valueClass);
    }

    static CustomObjectQuery<JsonNode> ofJsonNode() {
        return of(TypeReferences.jsonNodeTypeReference());
    }

    default CustomObjectQuery<T> byContainer(final String container) {
        return withPredicates(m -> m.container().is(container));
    }
}
