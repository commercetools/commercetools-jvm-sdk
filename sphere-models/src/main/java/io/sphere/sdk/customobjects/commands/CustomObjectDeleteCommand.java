package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.json.TypeReferences;

/**
 * Deletes a custom object in SPHERE.IO.
 * @param <T> type of the value of the custom object
 */
public interface CustomObjectDeleteCommand<T> extends DeleteCommand<CustomObject<T>> {
    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code typeReference}specified value type.
     * @param customObject the custom object to delete
     * @param typeReference the type reference to deserialize the updated custom object from the SPHERE.IO response
     * @return custom object
     */
    static <T> DeleteCommand<CustomObject<T>> of(final CustomObject<T> customObject, final TypeReference<T> typeReference) {
        return of(customObject.getContainer(), customObject.getKey(), typeReference);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code typeReference}specified value type.
     * @param customObject the custom object to delete
     * @param clazz the class of the value, if it not uses generics like lists, typically for POJOs
     * @return custom object
     */
    static <T> DeleteCommand<CustomObject<T>> of(final CustomObject<T> customObject, final Class<?> clazz) {
        return of(customObject.getContainer(), customObject.getKey(), clazz);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code typeReference}specified value type.
     * @param container the container name of the custom object to delete
     * @param key the key name of the custom object to delete
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String container, final String key, final TypeReference<T> typeReference) {
        return new CustomObjectDeleteCommandImpl<>(container, key, typeReference);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code typeReference}specified value type.
     * @param container the container name of the custom object to delete
     * @param key the key name of the custom object to delete
     * @param clazz the class of the value, if it not uses generics like lists, typically for POJOs
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String container, final String key, final Class<?> clazz) {
        return new CustomObjectDeleteCommandImpl<>(container, key, clazz);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with a JsonNode value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     * @param customObject the custom object to delete
     * @return custom object with a JsonNode value
     */
    static DeleteCommand<CustomObject<JsonNode>> ofJsonNode(final CustomObject<?> customObject) {
        return ofJsonNode(customObject.getContainer(), customObject.getKey());
    }

    /**
     *
     * @deprecated use {@link #ofJsonNode(CustomObject)} instead
     */
    @Deprecated
    static DeleteCommand<CustomObject<JsonNode>> of(final CustomObject<?> customObject) {
        return ofJsonNode(customObject);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and returns the old custom object with a JsonNode value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     * @param container the container name of the custom object to delete
     * @param key the key name of the custom object to delete
     * @return custom object with a JsonNode value
     */
    static DeleteCommand<CustomObject<JsonNode>> ofJsonNode(final String container, final String key) {
        return new CustomObjectDeleteCommandImpl<>(container, key, TypeReferences.jsonNodeTypeReference());
    }

    /**
     *
     * @deprecated use {@link #ofJsonNode(String, String)} instead
     */
    @Deprecated
    static DeleteCommand<CustomObject<JsonNode>> of(final String container, final String key) {
        return ofJsonNode(container, key);
    }
}
