package io.sphere.sdk.requests;

import io.sphere.sdk.models.Versioned;

public abstract class DeleteByIdCommandImpl<I> extends CommandImpl<I> {
    private final Versioned versionData;

    protected DeleteByIdCommandImpl(final Versioned versionData) {
        this.versionData = versionData;
    }

    @Override
    public HttpRequest httpRequest() {
        String baseEndpointWithoutId = baseEndpointWithoutId();
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        return HttpRequest.of(HttpMethod.DELETE, baseEndpointWithoutId + "/" + versionData.getId() + "?version=" + versionData.getVersion());
    }

    protected abstract String baseEndpointWithoutId();
}
