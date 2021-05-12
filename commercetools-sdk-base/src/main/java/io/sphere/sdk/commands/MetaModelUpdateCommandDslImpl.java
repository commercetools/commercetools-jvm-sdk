package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.expansion.ExpansionDslUtils;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.MetaModelExpansionDslExpansionModelRead;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.json.SphereJsonUtils.toJsonString;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.Objects.requireNonNull;

/**
 * Internal base class to implement commands that change one resource in the platform.
 *
 * @param <T> the type of the result of the command
 * @param <C> class which will serialized as JSON command body, most likely a template
 * @param <E> type of the expansion model
 */
public class MetaModelUpdateCommandDslImpl<T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>, E> extends CommandImpl<T> implements UpdateCommandDsl<T, C>, MetaModelExpansionDslExpansionModelRead<T, C, E> {
    final Versioned<T> versioned;
    final List<? extends UpdateAction<T>> updateActions;
    final JavaType javaType;
    final String baseEndpointWithoutId;
    final Function<MetaModelUpdateCommandDslBuilder<T, C, E>, C> creationFunction;
    final E expansionModel;
    final List<ExpansionPath<T>> expansionPaths;
    final List<NameValuePair> additionalHttpQueryParameters;

    private MetaModelUpdateCommandDslImpl(final Versioned<T> versioned,
                                          final List<? extends UpdateAction<T>> updateActions,
                                          final JavaType javaType,
                                          final String baseEndpointWithoutId,
                                          final Function<MetaModelUpdateCommandDslBuilder<T, C, E>, C> creationFunction,
                                          final E expansionModel,
                                          final List<ExpansionPath<T>> expansionPaths,
                                          final List<NameValuePair> additionalHttpQueryParameters) {
        this.expansionModel = requireNonNull(expansionModel);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.creationFunction = requireNonNull(creationFunction);
        this.versioned = requireNonNull(versioned);
        this.updateActions = requireNonNull(updateActions);
        this.javaType = requireNonNull(javaType);
        this.baseEndpointWithoutId = requireNonNull(baseEndpointWithoutId);
        this.additionalHttpQueryParameters = requireNonNull(additionalHttpQueryParameters);
    }

    protected MetaModelUpdateCommandDslImpl(final Versioned<T> versioned,
                                            final List<? extends UpdateAction<T>> updateActions,
                                            final JsonEndpoint<T> endpoint,
                                            final Function<MetaModelUpdateCommandDslBuilder<T, C, E>, C> creationFunction,
                                            final E expansionModel) {
        this(versioned, updateActions, SphereJsonUtils.convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), creationFunction, expansionModel, Collections.emptyList(), Collections.emptyList());
    }

    protected MetaModelUpdateCommandDslImpl(final MetaModelUpdateCommandDslBuilder<T, C, E> builder) {
        this(builder.getVersioned(), builder.getUpdateActions(), builder.getJacksonJavaType(), builder.getBaseEndpointWithoutId(), builder.getCreationFunction(), builder.expansionModel, builder.expansionPaths, builder.additionalHttpQueryParameters);
    }

    @Override
    protected JavaType jacksonJavaType() {
        return javaType;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        if (!baseEndpointWithoutId.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final String additions = queryParametersToString(true);
        final String path = baseEndpointWithoutId + "/" + getVersioned().getId() + (additions.length() > 1 ? additions : "");
        return HttpRequestIntent.of(HttpMethod.POST, path, toJsonString(new UpdateCommandBody<>(getVersioned().getVersion(), getUpdateActions())));
    }

    private String queryParametersToString(final boolean urlEncoded) {
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        expansionPaths().forEach(path -> builder.add("expand", path.toSphereExpand(), urlEncoded));
        additionalHttpQueryParameters().forEach(pair -> builder.add(pair.getName(), pair.getValue(), urlEncoded));
        return builder.toStringWithOptionalQuestionMark();
    }

    @Override
    public C withVersion(final Versioned<T> newVersioned) {
        return copyBuilder().versioned(newVersioned).build();
    }

    @Override
    public C withVersion(final Long version) {
        return withVersion(Versioned.of(getVersioned().getId(), version));
    }

    @Override
    public C withUpdateActions(List<? extends UpdateAction<T>> updateActions) {
         return copyBuilder().updateActions(updateActions).build();
    }

    @Override
    public C plusUpdateActions(final List<? extends UpdateAction<T>> updateActions) {
        return withUpdateActions(listOf(getUpdateActions(),updateActions));
    }

    public Versioned<T> getVersioned() {
        return versioned;
    }

    @Override
    public List<? extends UpdateAction<T>> getUpdateActions() {
        return updateActions;
    }

    protected MetaModelUpdateCommandDslBuilder<T, C, E> copyBuilder() {
        return new MetaModelUpdateCommandDslBuilder<>(this);
    }

    String getBaseEndpointWithoutId() {
        return baseEndpointWithoutId;
    }

    Function<MetaModelUpdateCommandDslBuilder<T, C, E>, C> getCreationFunction() {
        return creationFunction;
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
    public C withExpansionPaths(final String expansionPath) {
        return withExpansionPaths(ExpansionPath.of(expansionPath));
    }

    @Override
    public C plusExpansionPaths(final String expansionPath) {
        return plusExpansionPaths(ExpansionPath.of(expansionPath));
    }

    @Override
    public C plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return ExpansionDslUtils.plusExpansionPaths(this, m);
    }

    @Override
    public E expansionModel() {
        return expansionModel;
    }

    protected List<NameValuePair> additionalHttpQueryParameters() {
        return additionalHttpQueryParameters;
    }

    protected C withAdditionalHttpQueryParameters(final List<NameValuePair> pairs) {
        return copyBuilder().additionalHttpQueryParameters(pairs).build();
    }

    @Override
    public C withAdditionalHttpQueryParameters(final NameValuePair pair) {
        final List<NameValuePair> params = additionalHttpQueryParameters();
        final List<NameValuePair> resultingParameters = new LinkedList<>(params);
        resultingParameters.add(pair);
        return withAdditionalHttpQueryParameters(resultingParameters);
    }
}
