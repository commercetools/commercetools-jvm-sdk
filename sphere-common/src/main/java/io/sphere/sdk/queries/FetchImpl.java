package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;
import static io.sphere.sdk.queries.QueryParameterKeys.EXPAND;

public class FetchImpl<T> extends SphereRequestBase implements FetchDsl<T> {

    private final JsonEndpoint<T> endpoint;
    /**
     for example an ID, a key, slug, token
     */
    private final String identifierToSearchFor;
    private final List<ExpansionPath<T>> expansionPaths;
    private final List<HttpQueryParameter> additionalParameters;

    protected FetchImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor, final List<HttpQueryParameter> additionalParameters) {
        this(endpoint, identifierToSearchFor, Collections.emptyList(), additionalParameters);
    }

    protected FetchImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor) {
        this(endpoint, identifierToSearchFor, Collections.emptyList(), Collections.emptyList());
    }

    protected FetchImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor, final List<ExpansionPath<T>> expansionPaths, final List<HttpQueryParameter> additionalParameters) {
        this.endpoint = endpoint;
        this.identifierToSearchFor = identifierToSearchFor;
        this.expansionPaths = expansionPaths;
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

        final String queryParameters = "?" + builder.toString();
        final String path = endpoint.endpoint() + "/" + identifierToSearchFor + (queryParameters.length() > 1 ? queryParameters : "");
        return HttpRequestIntent.of(HttpMethod.GET, path);
    }

    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }


    protected List<HttpQueryParameter> additionalQueryParameters() {
        return Collections.emptyList();
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
    public FetchDsl<T> withExpansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        return new FetchImpl<>(endpoint, identifierToSearchFor, expansionPaths, additionalParameters);
    }
}
