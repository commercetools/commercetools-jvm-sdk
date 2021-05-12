package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.expansion.ExpansionDslUtils;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.expansion.MetaModelExpansionDslExpansionModelRead;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.json.SphereJsonUtils.convertToJavaType;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.Objects.requireNonNull;

/**
 * Internal base class to implement commands which deletes a resource by ID in the platform.
 *
 * @param <T> the type of the result of the command
 *
 */
public abstract class MetaModelByIdDeleteCommandImpl<T extends ResourceView<T, T>, C, E> extends CommandImpl<T> implements MetaModelExpansionDslExpansionModelRead<T, C, E>, DeleteCommand<T> {
    final Versioned<T> versioned;
    final boolean eraseData;
    final String endpoint;
    final JavaType javaType;
    final E expansionModel;
    final List<ExpansionPath<T>> expansionPaths;
    final Function<MetaModelByIdDeleteCommandBuilder<T, C, E>, C> creationFunction;

    protected MetaModelByIdDeleteCommandImpl(final Versioned<T> versioned,final boolean eraseData, final JavaType javaType, final String endpoint, final E expansionModel, final List<ExpansionPath<T>> expansionPaths, final Function<MetaModelByIdDeleteCommandBuilder<T, C, E>, C> creationFunction) {
        this.creationFunction = requireNonNull(creationFunction);
        this.expansionModel = requireNonNull(expansionModel);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.versioned = requireNonNull(versioned);
        this.endpoint = requireNonNull(endpoint);
        this.javaType = requireNonNull(javaType);
        this.eraseData = eraseData;
    }

    protected MetaModelByIdDeleteCommandImpl(final Versioned<T> versioned, final JsonEndpoint<T> endpoint, final E expansionModel, final Function<MetaModelByIdDeleteCommandBuilder<T, C, E>, C> creationFunction) {
        this(versioned, false, convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), expansionModel, Collections.emptyList(), creationFunction);
    }

    protected MetaModelByIdDeleteCommandImpl(final Versioned<T> versioned, final boolean eraseData, final JsonEndpoint<T> endpoint, final E expansionModel, final Function<MetaModelByIdDeleteCommandBuilder<T, C, E>, C> creationFunction) {
        this(versioned,eraseData, convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), expansionModel, Collections.emptyList(), creationFunction);
    }

    protected MetaModelByIdDeleteCommandImpl(final MetaModelByIdDeleteCommandBuilder<T, C, E> builder) {
        this(builder.versioned, builder.eraseData, builder.javaType, builder.endpoint, builder.expansionModel, builder.expansionPaths, builder.creationFunction);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String baseEndpointWithoutId = endpoint;
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash");
        }
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        expansionPaths().forEach(path -> builder.add("expand", path.toSphereExpand(), true));
        final String expansionPathParameters = builder.build();
        return HttpRequestIntent.of(HttpMethod.DELETE, baseEndpointWithoutId + "/" + versioned.getId() + "?version=" + versioned.getVersion()
                + (expansionPathParameters.isEmpty() ? "" : "&" + expansionPathParameters)
                + (eraseData ? "&dataErasure=true" : ""));
    }

    @Override
    protected JavaType jacksonJavaType() {
        return javaType;
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
        return ExpansionDslUtils.withExpansionPaths(this, expansionPath);
    }

    @Override
    public C withExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return ExpansionDslUtils.withExpansionPaths(this, m);
    }

    @Override
    public C plusExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return withExpansionPaths(listOf(expansionPaths(), expansionPaths));
    }

    @Override
    public C plusExpansionPaths(final ExpansionPath<T> expansionPath) {
        return ExpansionDslUtils.plusExpansionPaths(this, expansionPath);
    }

    @Override
    public C plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return ExpansionDslUtils.plusExpansionPaths(this, m);
    }

    @Override
    public C withExpansionPaths(final String expansionPath) {
        return withExpansionPaths(ExpansionPath.of(expansionPath));
    }

    @Override
    public C plusExpansionPaths(final String expansionPath) {
        return plusExpansionPaths(ExpansionPath.of(expansionPath));
    }

    @Override
    public E expansionModel() {
        return expansionModel;
    }


    protected MetaModelByIdDeleteCommandBuilder<T, C, E> copyBuilder() {
        return new MetaModelByIdDeleteCommandBuilder<>(this);
    }
}
