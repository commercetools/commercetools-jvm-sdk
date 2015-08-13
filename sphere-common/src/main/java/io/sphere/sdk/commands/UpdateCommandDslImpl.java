package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.json.SphereJsonUtils.toJsonString;
import static java.util.Objects.requireNonNull;

/**
 * Internal base class to implement commands that change one entity in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 */
public abstract class UpdateCommandDslImpl<T extends ResourceView<T>, C extends UpdateCommandDsl<T, C>> extends CommandImpl<T> implements UpdateCommandDsl<T, C> {
    private final Versioned<T> versioned;
    private final List<? extends UpdateAction<T>> updateActions;
    private final TypeReference<T> typeReference;
    private final String baseEndpointWithoutId;
    private final Function<UpdateCommandDslBuilder<T, C>, C> creationFunction;

    private UpdateCommandDslImpl(final Versioned<T> versioned, final List<? extends UpdateAction<T>> updateActions,
                                final TypeReference<T> typeReference, final String baseEndpointWithoutId, final Function<UpdateCommandDslBuilder<T, C>, C> creationFunction) {
        this.creationFunction = requireNonNull(creationFunction);
        this.versioned = requireNonNull(versioned);
        this.updateActions = requireNonNull(updateActions);
        this.typeReference = requireNonNull(typeReference);
        this.baseEndpointWithoutId = requireNonNull(baseEndpointWithoutId);
    }

    protected UpdateCommandDslImpl(final Versioned<T> versioned, final List<? extends UpdateAction<T>> updateActions,
                                final JsonEndpoint<T> endpoint, final Function<UpdateCommandDslBuilder<T, C>, C> creationFunction) {
        this(versioned, updateActions, endpoint.typeReference(), endpoint.endpoint(), creationFunction);
    }

    protected UpdateCommandDslImpl(final UpdateCommandDslBuilder<T, C> builder) {
        this(builder.getVersioned(), builder.getUpdateActions(), builder.getTypeReference(), builder.getBaseEndpointWithoutId(), builder.getCreationFunction());
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
        return HttpRequestIntent.of(HttpMethod.POST, path, toJsonString(new UpdateCommandBody<>(getVersioned().getVersion(), getUpdateActions())));
    }

    @Override
    public C withVersion(final Versioned<T> newVersioned) {
        return copyBuilder().versioned(newVersioned).build();
    }

    public Versioned<T> getVersioned() {
        return versioned;
    }

    public List<? extends UpdateAction<T>> getUpdateActions() {
        return updateActions;
    }

    protected UpdateCommandDslBuilder<T, C> copyBuilder() {
        return new UpdateCommandDslBuilder<>(this);
    }

    String getBaseEndpointWithoutId() {
        return baseEndpointWithoutId;
    }

    Function<UpdateCommandDslBuilder<T, C>, C> getCreationFunction() {
        return creationFunction;
    }

    TypeReference<T> getTypeReference() {
        return typeReference;
    }
}
