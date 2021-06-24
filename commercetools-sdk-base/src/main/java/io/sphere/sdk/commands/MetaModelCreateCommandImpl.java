package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.expansion.ExpansionDslUtils;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDslExpansionModelRead;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.UrlQueryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.json.SphereJsonUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.Objects.requireNonNull;

/**
 * Internal base class to implement commands which create a resource in the platform.
 *
 * @param <T> the type of the result of the command
 * @param <C> class which will serialized as JSON command body, most likely a template
 * @param <D> type of the draft object
 * @param <E> type of the expansion model
 */
public abstract class MetaModelCreateCommandImpl<T, C, D, E> extends CommandImpl<T> implements DraftBasedCreateCommand<T, D>, MetaModelExpansionDslExpansionModelRead<T, C, E> {

    final D body;
    final E expansionModel;
    final JavaType javaType;
    final String endpoint;
    final List<ExpansionPath<T>> expansionPaths;
    final Function<MetaModelCreateCommandBuilder<T, C, D, E>, C> creationFunction;

    protected MetaModelCreateCommandImpl(final D draft, final JavaType javaType, final String endpoint, final List<ExpansionPath<T>> expansionPaths, final E expansionModel, final Function<MetaModelCreateCommandBuilder<T, C, D, E>, C> creationFunction) {
        this.creationFunction = requireNonNull(creationFunction);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.expansionModel = requireNonNull(expansionModel);
        this.body = requireNonNull(draft);
        this.javaType = javaType;
        this.endpoint = endpoint;
    }

    protected MetaModelCreateCommandImpl(final MetaModelCreateCommandBuilder<T, C, D, E> builder) {
        this(builder.body, builder.javaType, builder.endpoint, builder.expansionPaths, builder.expansionModel, builder.creationFunction);
    }

    protected MetaModelCreateCommandImpl(final D draft, final JsonEndpoint<T> endpoint, final E expansionModel, final Function<MetaModelCreateCommandBuilder<T, C, D, E>, C> creationFunction) {
        this(draft, convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), Collections.emptyList(), expansionModel, creationFunction);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String additions = queryParametersToString(true);
        return HttpRequestIntent.of(httpMethod(), endpoint + (additions.length() > 1 ? additions : ""), httpBody());
    }

    private String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        expansionPaths().forEach(path -> builder.add("expand", path.toSphereExpand(), urlEncoded));
        return builder.toStringWithOptionalQuestionMark();
    }

    protected HttpMethod httpMethod() {
        return HttpMethod.POST;
    }

    protected String httpBody() {
        return toJsonString(body);
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

    @Override
    public D getDraft() {
        return body;
    }

    protected MetaModelCreateCommandBuilder<T, C, D, E> copyBuilder() {
        return new MetaModelCreateCommandBuilder<>(this);
    }
}
