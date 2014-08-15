package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.utils.JsonUtils;

/**
 * Base class to implement commands using the Jackson JSON mapper.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 */
public abstract class CommandImpl<T> extends Base implements Command<T> {
    @Override
    public Function<HttpResponse, T> resultMapper() {
        return httpResponse -> JsonUtils.readObjectFromJsonString(typeReference(), httpResponse.getResponseBody());
    }

    protected abstract TypeReference<T> typeReference();
}
