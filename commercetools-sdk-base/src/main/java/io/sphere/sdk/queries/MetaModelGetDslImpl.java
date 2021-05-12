package io.sphere.sdk.queries;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.client.SphereRequestUtils;
import io.sphere.sdk.expansion.ExpansionDslUtils;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.expansion.MetaModelExpansionDslExpansionModelRead;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;
import static io.sphere.sdk.queries.QueryParameterKeys.EXPAND;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static java.util.Objects.requireNonNull;

/**
 * @param <R> result type, maybe directly {@code T} or sth. like {@code List<T>}
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public abstract class MetaModelGetDslImpl<R, T, C extends MetaModelGetDsl<R, T, C, E>, E> extends Base implements MetaModelGetDsl<R, T, C, E>, MetaModelExpansionDslExpansionModelRead<T, C, E> {

    final JavaType javaType;
    final String endpoint;
    /**
     for example an ID, a key, slug, token
     */
    final String identifierToSearchFor;
    final List<ExpansionPath<T>> expansionPaths;
    final List<NameValuePair> additionalParameters;
    final E expansionModel;
    final Function<MetaModelGetDslBuilder<R, T, C, E>, C> builderFunction;

    protected MetaModelGetDslImpl(final JsonEndpoint<R> endpoint, final String identifierToSearchFor, final E expansionModel, final Function<MetaModelGetDslBuilder<R, T, C, E>, C> builderFunction, final List<NameValuePair> additionalParameters) {
        this(SphereJsonUtils.convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), identifierToSearchFor, Collections.emptyList(), expansionModel, builderFunction, additionalParameters);
    }

    protected MetaModelGetDslImpl(final String identifierToSearchFor, final JsonEndpoint<R> endpoint, final E expansionModel, final Function<MetaModelGetDslBuilder<R, T, C, E>, C> builderFunction) {
        this(SphereJsonUtils.convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), identifierToSearchFor, Collections.emptyList(), expansionModel, builderFunction, Collections.emptyList());
    }

    protected MetaModelGetDslImpl(final MetaModelGetDslBuilder<R, T, C, E> builder) {
        this(builder.javaType, builder.endpoint, builder.identifierToSearchFor, builder.expansionPaths, builder.expansionModel, builder.builderFunction, builder.additionalParameters);
    }

    protected MetaModelGetDslImpl(final JavaType javaType, final String endpoint, final String identifierToSearchFor, final List<ExpansionPath<T>> expansionPaths, final E expansionModel, final Function<MetaModelGetDslBuilder<R, T, C, E>, C> builderFunction, final List<NameValuePair> additionalParameters) {
        this.javaType = requireNonNull(javaType);
        this.endpoint = requireNonNull(endpoint);
        this.identifierToSearchFor = requireNonNull(identifierToSearchFor);
        this.expansionPaths = requireNonNull(expansionPaths);
        this.expansionModel = requireNonNull(expansionModel);
        this.builderFunction = requireNonNull(builderFunction);
        this.additionalParameters = requireNonNull(additionalParameters);
    }

    @Nullable
    @Override
    public R deserialize(final HttpResponse httpResponse) {
        return Optional.of(httpResponse)
                .filter(r -> r.getStatusCode() != NOT_FOUND_404)
                .map(r -> SphereRequestUtils.<R>deserialize(r, jacksonJavaType()))
                .orElse(null);
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        if (!endpoint.startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final boolean urlEncoded = true;
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalQueryParameters().forEach(parameter -> builder.add(parameter.getName(), parameter.getValue(), urlEncoded));
        final String queryParameters = builder.toStringWithOptionalQuestionMark();
        final String path = endpoint + "/" + identifierToSearchFor + (queryParameters.length() > 1 ? queryParameters : "");
        return HttpRequestIntent.of(HttpMethod.GET, path);
    }

    protected JavaType jacksonJavaType() {
        return javaType;
    }


    protected List<NameValuePair> additionalQueryParameters() {
        return additionalParameters;
    }

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() || httpResponse.getStatusCode() == NOT_FOUND_404;
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
        return ExpansionDslUtils.plusExpansionPaths(this, Collections.singletonList(expansionPath));
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

    protected MetaModelGetDslBuilder<R, T, C, E> copyBuilder() {
        return new MetaModelGetDslBuilder<>(this);
    }

    protected C withAdditionalQueryParameters(final List<NameValuePair> pairs) {
        return copyBuilder().additionalQueryParameters(pairs).build();
    }
}
