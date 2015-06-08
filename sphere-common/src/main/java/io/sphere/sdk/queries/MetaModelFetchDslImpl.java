package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.UrlQueryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;
import static io.sphere.sdk.queries.QueryParameterKeys.EXPAND;
import static io.sphere.sdk.utils.ListUtils.listOf;
import static java.util.Arrays.asList;

public class MetaModelFetchDslImpl<T, C extends MetaModelFetchDsl<T, C, E>, E> extends SphereRequestBase implements MetaModelFetchDsl<T, C, E> {

    final JsonEndpoint<T> endpoint;
    /**
     for example an ID, a key, slug, token
     */
    final String identifierToSearchFor;
    final List<ExpansionPath<T>> expansionPaths;
    final List<HttpQueryParameter> additionalParameters;
    final E expansionModel;
    final Function<MetaModelFetchDslBuilder<T, C, E>, C> builderFunction;

    protected MetaModelFetchDslImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor, final E expansionModel, final Function<MetaModelFetchDslBuilder<T, C, E>, C> builderFunction, final List<HttpQueryParameter> additionalParameters) {
        this(endpoint, identifierToSearchFor, Collections.emptyList(), expansionModel, builderFunction, additionalParameters);
    }

    protected MetaModelFetchDslImpl(final String identifierToSearchFor, final JsonEndpoint<T> endpoint, final E expansionModel, final Function<MetaModelFetchDslBuilder<T, C, E>, C> builderFunction) {
        this(endpoint, identifierToSearchFor, Collections.emptyList(), expansionModel, builderFunction, Collections.emptyList());
    }

    protected MetaModelFetchDslImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor, final E expansionModel, final Function<MetaModelFetchDslBuilder<T, C, E>, C> builderFunction) {
        this(endpoint, identifierToSearchFor, Collections.emptyList(), expansionModel, builderFunction, Collections.emptyList());
    }

    public MetaModelFetchDslImpl(final MetaModelFetchDslBuilder<T, C, E> builder) {
        this(builder.endpoint, builder.identifierToSearchFor, builder.expansionPaths, builder.expansionModel, builder.builderFunction, builder.additionalParameters);
    }

    protected MetaModelFetchDslImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor, final List<ExpansionPath<T>> expansionPaths, final E expansionModel, final Function<MetaModelFetchDslBuilder<T, C, E>, C> builderFunction, final List<HttpQueryParameter> additionalParameters) {
        this.endpoint = endpoint;
        this.identifierToSearchFor = identifierToSearchFor;
        this.expansionPaths = expansionPaths;
        this.expansionModel = expansionModel;
        this.builderFunction = builderFunction;
        this.additionalParameters = additionalParameters;
    }

    @Override
    public Optional<T> deserialize(final HttpResponse httpResponse) {
        return Optional.of(httpResponse)
                .filter(r -> r.getStatusCode() != NOT_FOUND_404)
                .map(r -> resultMapperOf(typeReference()).apply(httpResponse));
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        if (!endpoint.endpoint().startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final boolean urlEncoded = true;
        final UrlQueryBuilder builder = UrlQueryBuilder.of();
        expansionPaths().forEach(path -> builder.add(EXPAND, path.toSphereExpand(), urlEncoded));
        additionalQueryParameters().forEach(parameter -> builder.add(parameter.getKey(), parameter.getValue(), urlEncoded));
        final String queryParameters = builder.toStringWithOptionalQuestionMark();
        final String path = endpoint.endpoint() + "/" + identifierToSearchFor + (queryParameters.length() > 1 ? queryParameters : "");
        return HttpRequestIntent.of(HttpMethod.GET, path);
    }

    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }


    protected List<HttpQueryParameter> additionalQueryParameters() {
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
    public final C plusExpansionPath(final ExpansionPath<T> expansionPath) {
        return withExpansionPath(listOf(expansionPaths(), expansionPath));
    }

    @Override
    public final C plusExpansionPath(final Function<E, ExpansionPath<T>> m) {
        return plusExpansionPath(m.apply(expansionModel));
    }

    @Override
    public final C withExpansionPath(final List<ExpansionPath<T>> expansionPaths) {
        return copyBuilder().expansionPaths(expansionPaths).build();
    }

    @Override
    public final C withExpansionPath(final Function<E, ExpansionPath<T>> m) {
        return withExpansionPath(asList(m.apply(expansionModel)));
    }

    @Override
    public final C withExpansionPath(final ExpansionPath<T> expansionPath) {
        return withExpansionPath(asList(expansionPath));
    }

    @Override
    public Fetch<T> toFetch() {
        return this;
    }

    protected MetaModelFetchDslBuilder<T, C, E> copyBuilder() {
        return new MetaModelFetchDslBuilder<>(this);
    }
}
