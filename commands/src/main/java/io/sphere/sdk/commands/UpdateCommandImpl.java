package io.sphere.sdk.commands;

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
public abstract class UpdateCommandImpl<T> extends CommandImpl<T> implements UpdateCommand<T> {
    private final Versioned<T> versioned;
    private final List<UpdateAction<T>> updateActions;

    protected UpdateCommandImpl(final Versioned<T> versioned, final List<UpdateAction<T>> updateActions) {
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
