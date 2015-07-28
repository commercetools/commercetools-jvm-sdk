package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;

/**
 * Deletes a custom object in SPHERE.IO.
 *
 *
 */
public interface CustomObjectDeleteCommand<T extends CustomObject<?>> extends DeleteCommand<T> {
    static <T extends CustomObject<?>> DeleteCommand<T> of(final T customObject, final TypeReference<T> typeReference) {
        return of(customObject.getContainer(), customObject.getKey(), typeReference);
    }

    static <T extends CustomObject<?>> DeleteCommand<T> of(final String container, final String key, final TypeReference<T> typeReference) {
        return new CustomObjectDeleteCommandImpl<>(container, key, typeReference);
    }

    /**
     * Deletes a custom object. Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     * @param customObject the custom object to delete
     * @param <T> the type of the whole custom object
     * @return custom object only with meta data
     */
    static <T extends CustomObject<?>> DeleteCommand<CustomObject<JsonNode>> of(final T customObject) {
        return of(customObject.getContainer(), customObject.getKey());
    }

    static DeleteCommand<CustomObject<JsonNode>> of(final String container, final String key) {
        return new CustomObjectDeleteCommandImpl<>(container, key, new TypeReference<CustomObject<JsonNode>>() {

        });
    }
}
