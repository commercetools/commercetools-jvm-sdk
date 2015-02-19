package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.HttpMethod;

import static java.lang.String.format;

/**
 * Deletes a custom object in SPHERE.IO.
 *
 *
 */
public final class CustomObjectDeleteByContainerAndKeyCommand<T extends CustomObject<?>> extends CommandImpl<T> implements DeleteCommand<T> {
    private final String container;
    private final String key;
    private final TypeReference<T> typeReference;

    @Override
    protected TypeReference<T> typeReference() {
        return typeReference;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.DELETE, CustomObjectsEndpoint.PATH + format("/%s/%s", container, key));
    }

    private CustomObjectDeleteByContainerAndKeyCommand(final String container, final String key, final TypeReference<T> typeReference) {
        this.container = CustomObject.validatedContainer(container);
        this.key = CustomObject.validatedKey(key);
        this.typeReference = typeReference;
    }

    public static <T extends CustomObject<?>> CustomObjectDeleteByContainerAndKeyCommand<T> of(final T customObject, final TypeReference<T> typeReference) {
        return of(customObject.getContainer(), customObject.getKey(), typeReference);
    }

    public static <T extends CustomObject<?>> CustomObjectDeleteByContainerAndKeyCommand<T> of(final String container, final String key, final TypeReference<T> typeReference) {
        return new CustomObjectDeleteByContainerAndKeyCommand<>(container, key, typeReference);
    }

    /**
     * Deletes a custom object. Convenience method to not specify the {@link com.fasterxml.jackson.core.type.TypeReference} but lacking the accessible value in the result.
     * @param customObject the custom object to delete
     * @param <T> the type of the whole custom object
     * @return custom object only with meta data
     */
    public static <T extends CustomObject<?>> CustomObjectDeleteByContainerAndKeyCommand<CustomObject<JsonNode>> of(final T customObject) {
        return of(customObject.getContainer(), customObject.getKey());
    }

    public static CustomObjectDeleteByContainerAndKeyCommand<CustomObject<JsonNode>> of(final String container, final String key) {
        return new CustomObjectDeleteByContainerAndKeyCommand<>(container, key, new TypeReference<CustomObject<JsonNode>>() {

        });
    }
}
