package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.models.Versioned;

import static java.util.Objects.requireNonNull;

/**
 Internal base class to implement commands which deletes an entity by ID in SPHERE.IO.
 @param <T> the type of the result of the command, most likely the updated entity without expanded references */
public abstract class ByIdDeleteCommandImpl<T> extends CommandImpl<T> implements ByIdDeleteCommand<T> {
    private final Versioned<T> versioned;
    private final JsonEndpoint<T> endpoint;

    protected ByIdDeleteCommandImpl(final Versioned<T> versioned, final JsonEndpoint<T> endpoint) {
        this.versioned = requireNonNull(versioned);
        this.endpoint = requireNonNull(endpoint);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String baseEndpointWithoutId = endpoint.endpoint();
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        return HttpRequestIntent.of(HttpMethod.DELETE, baseEndpointWithoutId + "/" + versioned.getId() + "?version=" + versioned.getVersion());
    }

    @Override
    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }
}
