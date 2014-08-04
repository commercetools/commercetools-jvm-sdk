package io.sphere.sdk.requests;

import io.sphere.sdk.models.Versioned;

public abstract class DeleteByIdCommandImpl<I> extends CommandImpl<I> {
    private final Versioned versioned;

    protected DeleteByIdCommandImpl(final Versioned versioned) {
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
