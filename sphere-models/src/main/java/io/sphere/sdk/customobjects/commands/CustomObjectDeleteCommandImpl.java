package io.sphere.sdk.customobjects.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectUtils;
import io.sphere.sdk.http.HttpMethod;

import static java.lang.String.format;

/**
 * Deletes a custom object in SPHERE.IO.
 *
 *
 */
final class CustomObjectDeleteCommandImpl<T> extends CommandImpl<CustomObject<T>> implements CustomObjectDeleteCommand<T> {
    private final String container;
    private final String key;
    private final TypeReference<T> typeReference;

    @Override
    protected JavaType jacksonJavaType() {
        return CustomObjectUtils.getCustomObjectJavaTypeForValue(typeReference);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.DELETE, CustomObjectEndpoint.PATH + format("/%s/%s", container, key));
    }

    CustomObjectDeleteCommandImpl(final String container, final String key, final TypeReference<T> typeReference) {
        this.container = CustomObject.validatedContainer(container);
        this.key = CustomObject.validatedKey(key);
        this.typeReference = typeReference;
    }
}
