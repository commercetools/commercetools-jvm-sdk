package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.models.Base;

/**
 * Base class to implement commands using the Jackson JSON mapper.
 *
 * @param <T> the type of the result of the command
 *
 */
public abstract class CommandImpl<T> extends Base implements Command<T> {
    protected CommandImpl() {
    }

    @Override
    public T deserialize(final HttpResponse httpResponse) {
        return SphereRequestUtils.deserialize(httpResponse, jacksonJavaType());
    }

    protected abstract JavaType jacksonJavaType();
}
