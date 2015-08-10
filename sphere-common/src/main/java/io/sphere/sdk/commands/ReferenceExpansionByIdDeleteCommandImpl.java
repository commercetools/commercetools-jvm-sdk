package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.expansion.ExpansionDslUtil;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDslExpansionModelRead;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.models.Versioned;

import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.Objects.requireNonNull;

/**
 Internal base class to implement commands which deletes an entity by ID in SPHERE.IO.
 @param <T> the type of the result of the command, most likely the updated entity without expanded references */
public abstract class ReferenceExpansionByIdDeleteCommandImpl<T, C, E> extends CommandImpl<T> implements ByIdDeleteCommand<T>, MetaModelExpansionDslExpansionModelRead<T, C, E> {
    private final Versioned<T> versioned;
    private final JsonEndpoint<T> endpoint;
    private final E expansionModel;
    private final List<ExpansionPath<T>> expansionPaths;

    protected ReferenceExpansionByIdDeleteCommandImpl(final Versioned<T> versioned, final JsonEndpoint<T> endpoint, final E expansionModel, final List<ExpansionPath<T>> expansionPaths) {
        this.expansionModel = expansionModel;
        this.expansionPaths = expansionPaths;
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

    @Override
    public List<ExpansionPath<T>> expansionPaths() {
        return expansionPaths;
    }

    @Override
    public final C withExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return copyBuilder().expansionPaths(expansionPaths).build();
    }

    @Override
    public C withExpansionPaths(final ExpansionPath<T> expansionPath) {
        return ExpansionDslUtil.withExpansionPaths(this, expansionPath);
    }

    @Override
    public C withExpansionPaths(final Function<E, ExpansionPath<T>> m) {
        return ExpansionDslUtil.withExpansionPaths(this, m);
    }

    @Override
    public C plusExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return withExpansionPaths(listOf(expansionPaths(), expansionPaths));
    }

    @Override
    public C plusExpansionPaths(final ExpansionPath<T> expansionPath) {
        return ExpansionDslUtil.plusExpansionPaths(this, expansionPath);
    }

    @Override
    public C plusExpansionPaths(final Function<E, ExpansionPath<T>> m) {
        return ExpansionDslUtil.plusExpansionPaths(this, m);
    }

    @Override
    public E expansionModel() {
        return expansionModel;
    }
}
