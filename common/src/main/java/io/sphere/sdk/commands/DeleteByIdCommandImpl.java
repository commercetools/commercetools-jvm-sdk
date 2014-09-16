package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.http.JsonEndpoint;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;

/**
 * Base class to implement commands which deletes an entity by ID in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 * {@include.example example.CategoryLifecycleExample#delete()}
 */
public abstract class DeleteByIdCommandImpl<T> extends CommandImpl<T> implements DeleteByIdCommand<T> {
    private final Versioned<T> versioned;
    private final JsonEndpoint<T> endpoint;

    protected DeleteByIdCommandImpl(final Versioned<T> versioned, final JsonEndpoint<T> endpoint) {
        this.versioned = versioned;
        this.endpoint = endpoint;
    }

    @Override
    public HttpRequest httpRequest() {
        final String baseEndpointWithoutId = endpoint.endpoint();
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        return HttpRequest.of(HttpMethod.DELETE, baseEndpointWithoutId + "/" + versioned.getId() + "?version=" + versioned.getVersion());
    }

    @Override
    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }
}
