package io.sphere.sdk.commands;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpRequest;

public abstract class DeleteByIdCommandImpl<I> extends CommandImpl<I> {
    private final Versioned<I> versioned;

    protected DeleteByIdCommandImpl(final Versioned<I> versioned) {
        this.versioned = versioned;
    }

    @Override
    public HttpRequest httpRequest() {
        final String baseEndpointWithoutId = baseEndpointWithoutId();
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        return HttpRequest.of(HttpMethod.DELETE, baseEndpointWithoutId + "/" + versioned.getId() + "?version=" + versioned.getVersion());
    }

    protected abstract String baseEndpointWithoutId();
}
