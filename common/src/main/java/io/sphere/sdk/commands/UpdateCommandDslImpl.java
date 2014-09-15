package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;

import java.util.List;

import static io.sphere.sdk.utils.JsonUtils.toJson;

/**
 * Base class to implement commands that change one entity in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 */
public class UpdateCommandDslImpl<T> extends CommandImpl<T> implements UpdateCommandDsl<T> {
    private final Versioned<T> versioned;
    private final List<UpdateAction<T>> updateActions;
    private final TypeReference<T> typeReference;
    private final String baseEndpointWithoutId;

    public UpdateCommandDslImpl(final Versioned<T> versioned, final List<UpdateAction<T>> updateActions,
                                final TypeReference<T> typeReference, final String baseEndpointWithoutId) {
        this.versioned = versioned;
        this.updateActions = updateActions;
        this.typeReference = typeReference;
        this.baseEndpointWithoutId = baseEndpointWithoutId;
    }

    @Override
    protected TypeReference<T> typeReference() {
        return typeReference;
    }

    @Override
    public HttpRequest httpRequest() {
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final String path = baseEndpointWithoutId + "/" + versioned.getId();
        return HttpRequest.of(HttpMethod.POST, path, toJson(new UpdateCommandBody<T>(versioned.getVersion(), updateActions)));
    }

    @Override
    public UpdateCommandDsl<T> withVersion(final Versioned<T> newVersioned) {
        return new UpdateCommandDslImpl<T>(newVersioned, updateActions, typeReference, baseEndpointWithoutId);
    }
}
