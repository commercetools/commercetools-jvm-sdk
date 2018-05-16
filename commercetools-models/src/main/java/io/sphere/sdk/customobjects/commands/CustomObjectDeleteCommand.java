package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.json.TypeReferences;

/**
 * <p>Deletes a custom object in the platform.</p>
 *
 * {@include.example io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommandIntegrationTest#demo()}
 *
 * @param <T> type of the value of the custom object
 * @see CustomObject
 */
public interface CustomObjectDeleteCommand<T> extends DeleteCommand<CustomObject<T>> {
    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     * @param customObject the custom object to delete
     * @param valueTypeReference the type reference to deserialize the updated custom object from the platform response
     * @param <T> type of the value of the custom object
     * @return custom object
     */
    static <T> DeleteCommand<CustomObject<T>> of(final CustomObject<T> customObject, final TypeReference<T> valueTypeReference) {
        return of(customObject.getContainer(), customObject.getKey(), valueTypeReference);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     * @param customObject the custom object to delete
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T> type of the value of the custom object
     * @return custom object
     */
    static <T> DeleteCommand<CustomObject<T>> of(final CustomObject<T> customObject, final Class<T> valueClass) {
        return of(customObject.getContainer(), customObject.getKey(), valueClass);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     * @param container the container name of the custom object to delete
     * @param key the key name of the custom object to delete
     * @param valueTypeReference the type reference to deserialize the updated custom object from the platform response
     * @param <T> type of the value of the custom object
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String container, final String key, final TypeReference<T> valueTypeReference) {
        return new CustomObjectDeleteCommandImpl<>(container, key, valueTypeReference);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     * @param container the container name of the custom object to delete
     * @param key the key name of the custom object to delete
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T> type of the value of the custom object
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String container, final String key, final Class<T> valueClass) {
        return new CustomObjectDeleteCommandImpl<>(container, key, valueClass);
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
     * Deletes a custom object by id with optimistic concurrency control and returns the old custom object with a JsonNode value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     * @param id the id of the custom object to delete
     * @param version the version of the custom object to delete
     * @return custom object with a JsonNode value
     */
    static DeleteCommand<CustomObject<JsonNode>> ofJsonNode(final String id, final Long version) {
        return of(id, version, TypeReferences.jsonNodeTypeReference());
    }

    /**
     * Deletes a custom object by id with optimistic concurrency control and returns the old custom object with the in {@code valueTypeReference} specified value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     * @param id the id of the custom object to delete
     * @param version the version of the custom object to delete
     * @param valueTypeReference the type reference to deserialize the updated custom object from the platform response
     * @param <T> type of the value of the custom object
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String id, final Long version, final TypeReference<T> valueTypeReference) {
        return new CustomObjectDeleteCommandImpl<>(id, version, valueTypeReference);
    }

    /**
     * Deletes a custom object by id with optimistic concurrency control and returns the old custom object with the in {@code valueClass} specified value type.
     *
     * {@include.example io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommandIntegrationTest#demoById()}
     *
     * @param id the id of the custom object to delete
     * @param version the version of the custom object to delete
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T> type of the value of the custom object
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String id, final Long version, final Class<T> valueClass) {
        return new CustomObjectDeleteCommandImpl<>(id, version, valueClass);
    }


    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     *
     * @param customObject       the custom object to delete
     * @param valueTypeReference the type reference to deserialize the updated custom object from the platform response
     * @param eraseData          If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @param <T>                type of the value of the custom object
     * @return custom object
     */
    static <T> DeleteCommand<CustomObject<T>> of(final CustomObject<T> customObject, final TypeReference<T> valueTypeReference, final boolean eraseData) {
        return of(customObject.getContainer(), customObject.getKey(), valueTypeReference, eraseData);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     *
     * @param customObject the custom object to delete
     * @param valueClass   the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T>          type of the value of the custom object
     * @param eraseData    If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object
     */
    static <T> DeleteCommand<CustomObject<T>> of(final CustomObject<T> customObject, final Class<T> valueClass, final boolean eraseData) {
        return of(customObject.getContainer(), customObject.getKey(), valueClass, eraseData);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     *
     * @param container          the container name of the custom object to delete
     * @param key                the key name of the custom object to delete
     * @param valueTypeReference the type reference to deserialize the updated custom object from the platform response
     * @param <T>                type of the value of the custom object
     * @param eraseData          If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String container, final String key, final TypeReference<T> valueTypeReference, final boolean eraseData) {
        return new CustomObjectDeleteCommandImpl<>(container, key, eraseData, valueTypeReference);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with the in {@code valueTypeReference}specified value type.
     *
     * @param container  the container name of the custom object to delete
     * @param key        the key name of the custom object to delete
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T>        type of the value of the custom object
     * @param eraseData  If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String container, final String key, final Class<T> valueClass, final boolean eraseData) {
        return new CustomObjectDeleteCommandImpl<>(container, key, eraseData, valueClass);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and uses the delete endpoint via container and key and returns the old custom object with a JsonNode value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     *
     * @param customObject the custom object to delete
     * @param eraseData    If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static DeleteCommand<CustomObject<JsonNode>> ofJsonNode(final CustomObject<?> customObject, final boolean eraseData) {
        return ofJsonNode(customObject.getContainer(), customObject.getKey(), eraseData);
    }

    /**
     * Deletes a custom object without optimistic concurrency control and returns the old custom object with a JsonNode value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     *
     * @param container the container name of the custom object to delete
     * @param key       the key name of the custom object to delete
     * @param eraseData If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static DeleteCommand<CustomObject<JsonNode>> ofJsonNode(final String container, final String key, final boolean eraseData) {
        return new CustomObjectDeleteCommandImpl<>(container, key, eraseData, TypeReferences.jsonNodeTypeReference());
    }

    /**
     * Deletes a custom object by id with optimistic concurrency control and returns the old custom object with a JsonNode value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     *
     * @param id        the id of the custom object to delete
     * @param version   the version of the custom object to delete
     * @param eraseData If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static DeleteCommand<CustomObject<JsonNode>> ofJsonNode(final String id, final Long version, final boolean eraseData) {
        return of(id, version, TypeReferences.jsonNodeTypeReference(), eraseData);
    }

    /**
     * Deletes a custom object by id with optimistic concurrency control and returns the old custom object with the in {@code valueTypeReference} specified value type.
     * Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     *
     * @param id                 the id of the custom object to delete
     * @param version            the version of the custom object to delete
     * @param valueTypeReference the type reference to deserialize the updated custom object from the platform response
     * @param <T>                type of the value of the custom object
     * @param eraseData          If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String id, final Long version, final TypeReference<T> valueTypeReference, final boolean eraseData) {
        return new CustomObjectDeleteCommandImpl<>(id, version, eraseData, valueTypeReference);
    }

    /**
     * Deletes a custom object by id with optimistic concurrency control and returns the old custom object with the in {@code valueClass} specified value type.
     * <p>
     * {@include.example io.sphere.sdk.customobjects.commands.CustomObjectDeleteCommandIntegrationTest#demoById()}
     *
     * @param id         the id of the custom object to delete
     * @param version    the version of the custom object to delete
     * @param valueClass the class of the value, if it not uses generics like lists, typically for POJOs
     * @param <T>        type of the value of the custom object
     * @param eraseData  If set to {@literal true},  commercetools platform guarantees that all personal data related to the particular object, including invisible data, is erased, in compliance with the GDPR.
     * @return custom object with a JsonNode value
     */
    static <T> DeleteCommand<CustomObject<T>> of(final String id, final Long version, final Class<T> valueClass, final boolean eraseData) {
        return new CustomObjectDeleteCommandImpl<>(id, version, eraseData, valueClass);
    }
}
