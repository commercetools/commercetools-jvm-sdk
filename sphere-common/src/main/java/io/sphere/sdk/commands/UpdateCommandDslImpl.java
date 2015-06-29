package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.http.HttpMethod;

import java.util.List;

import static io.sphere.sdk.json.JsonUtils.toJson;
import static java.util.Objects.requireNonNull;

/**
 * Base class to implement commands that change one entity in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 */
public class UpdateCommandDslImpl<T> extends CommandImpl<T> implements UpdateCommandDsl<T> {
    private final Versioned<T> versioned;
    private final List<? extends UpdateAction<T>> updateActions;
    private final TypeReference<T> typeReference;
    private final String baseEndpointWithoutId;

    private UpdateCommandDslImpl(final Versioned<T> versioned, final List<? extends UpdateAction<T>> updateActions,
                                final TypeReference<T> typeReference, final String baseEndpointWithoutId) {
        this.versioned = requireNonNull(versioned);
        this.updateActions = requireNonNull(updateActions);
        this.typeReference = requireNonNull(typeReference);
        this.baseEndpointWithoutId = requireNonNull(baseEndpointWithoutId);
    }

    protected UpdateCommandDslImpl(final Versioned<T> versioned, final List<? extends UpdateAction<T>> updateActions,
                                final JsonEndpoint<T> endpoint) {
        this(versioned, updateActions, endpoint.typeReference(), endpoint.endpoint());
    }

    @Override
    protected TypeReference<T> typeReference() {
        return typeReference;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final String path = baseEndpointWithoutId + "/" + getVersioned().getId();
        return HttpRequestIntent.of(HttpMethod.POST, path, toJson(new UpdateCommandBody<>(getVersioned().getVersion(), getUpdateActions())));
    }

    @Override
    public UpdateCommandDsl<T> withVersion(final Versioned<T> newVersioned) {
        return new UpdateCommandDslImpl<>(newVersioned, getUpdateActions(), typeReference, baseEndpointWithoutId);
    }

    public Versioned<T> getVersioned() {
        return versioned;
    }

    public List<? extends UpdateAction<T>> getUpdateActions() {
        return updateActions;
    }
}
