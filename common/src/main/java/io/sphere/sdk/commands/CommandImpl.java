package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;

import io.sphere.sdk.client.ClientRequestBase;
import io.sphere.sdk.http.HttpResponse;

/**
 * Base class to implement commands using the Jackson JSON mapper.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 */
public abstract class CommandImpl<T> extends ClientRequestBase implements Command<T> {
    @Override
    public Function<HttpResponse, T> resultMapper() {
        return resultMapperOf(typeReference());
    }

    protected abstract TypeReference<T> typeReference();
}
