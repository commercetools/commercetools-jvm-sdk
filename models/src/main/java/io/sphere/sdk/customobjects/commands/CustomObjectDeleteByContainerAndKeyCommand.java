package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;

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
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.DELETE, CustomObjectsEndpoint.PATH + format("/%s/%s", container, key));
    }

    private CustomObjectDeleteByContainerAndKeyCommand(final String container, final String key, final TypeReference<T> typeReference) {
        this.container = container;
        this.key = key;
        this.typeReference = typeReference;
    }

    public static <T extends CustomObject<?>> CustomObjectDeleteByContainerAndKeyCommand<T> of(final T customObject, final TypeReference<T> typeReference) {
        return of(customObject.getContainer(), customObject.getKey(), typeReference);
    }

    public static <T extends CustomObject<?>> CustomObjectDeleteByContainerAndKeyCommand<T> of(final String container, final String key, final TypeReference<T> typeReference) {
        return new CustomObjectDeleteByContainerAndKeyCommand<>(container, key, typeReference);
    }
}
