package io.sphere.sdk.requests;

import io.sphere.sdk.annotations.Internal;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static io.sphere.sdk.utils.JsonUtils.toJson;

@Internal
public abstract class UpdateCommandImpl<T> extends CommandImpl<T> {
    private final Versioned versioned;
    private final List<UpdateAction<T>> updateActions;

    protected UpdateCommandImpl(final Versioned versioned, final List<UpdateAction<T>> updateActions) {
        this.versioned = versioned;
        this.updateActions = updateActions;
    }

    @Override
    public HttpRequest httpRequest() {
        final String baseEndpointWithoutId = baseEndpointWithoutId();
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final String path = baseEndpointWithoutId + "/" + versioned.getId();
        return HttpRequest.of(HttpMethod.POST, path, toJson(new UpdateCommandBody<T>(versioned.getVersion(), updateActions)));
    }

    protected abstract String baseEndpointWithoutId();
}
