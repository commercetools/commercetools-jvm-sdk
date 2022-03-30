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
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.http.NameValuePair;
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
 */
public abstract class MetaModelHeadDslImpl<R, T, C extends MetaModelHeadDsl<R, T, C>> extends Base implements MetaModelHeadDsl<R, T, C> {

    final JavaType javaType;
    final String endpoint;
    /**
     for example an ID, a key, slug, token
     */
    final String identifierToSearchFor;
    final List<NameValuePair> additionalParameters;
    final Function<MetaModelHeadDslBuilder<R, T, C>, C> builderFunction;

    protected MetaModelHeadDslImpl(final JsonEndpoint<R> endpoint, final String identifierToSearchFor, final Function<MetaModelHeadDslBuilder<R, T, C>, C> builderFunction, final List<NameValuePair> additionalParameters) {
        this(SphereJsonUtils.convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), identifierToSearchFor, builderFunction, additionalParameters);
    }

    protected MetaModelHeadDslImpl(final String identifierToSearchFor, final JsonEndpoint<R> endpoint, final Function<MetaModelHeadDslBuilder<R, T, C>, C> builderFunction) {
        this(SphereJsonUtils.convertToJavaType(endpoint.typeReference()), endpoint.endpoint(), identifierToSearchFor, builderFunction, Collections.emptyList());
    }

    protected MetaModelHeadDslImpl(final MetaModelHeadDslBuilder<R, T, C> builder) {
        this(builder.javaType, builder.endpoint, builder.identifierToSearchFor, builder.builderFunction, builder.additionalParameters);
    }

    protected MetaModelHeadDslImpl(final JavaType javaType, final String endpoint, @Nullable final String identifierToSearchFor, final Function<MetaModelHeadDslBuilder<R, T, C>, C> builderFunction, final List<NameValuePair> additionalParameters) {
        this.javaType = requireNonNull(javaType);
        this.endpoint = requireNonNull(endpoint);
        this.identifierToSearchFor = identifierToSearchFor;
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
        additionalQueryParameters().forEach(parameter -> builder.add(parameter.getName(), parameter.getValue(), urlEncoded));
        final String queryParameters = builder.toStringWithOptionalQuestionMark();
        final String path = endpoint + "/" + identifierToSearchFor + (queryParameters.length() > 1 ? queryParameters : "");
        return HttpRequestIntent.of(HttpMethod.HEAD, path);
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

    protected MetaModelHeadDslBuilder<R, T, C> copyBuilder() {
        return new MetaModelHeadDslBuilder<>(this);
    }

    protected C withAdditionalQueryParameters(final List<NameValuePair> pairs) {
        return copyBuilder().additionalQueryParameters(pairs).build();
    }
}
