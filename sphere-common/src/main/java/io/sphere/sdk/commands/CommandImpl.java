package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;

/**
 * Base class to implement commands using the Jackson JSON mapper.
 *
 * @param <T> the type of the result of the command, most likely the updated resource without expanded references
 *
 */
public abstract class CommandImpl<T> extends SphereRequestBase implements Command<T> {
    @Override
    public T deserialize(final HttpResponse httpResponse) {
        return deserialize(httpResponse, jacksonJavaType());
    }

    protected abstract JavaType jacksonJavaType();
}
