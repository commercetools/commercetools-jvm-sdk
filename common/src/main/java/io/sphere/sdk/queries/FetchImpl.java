package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.*;
import io.sphere.sdk.http.UrlQueryBuilder;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;

public abstract class FetchImpl<T> extends SphereRequestBase implements Fetch<T> {

    private final JsonEndpoint<T> endpoint;
    /**
     for example an ID, a key, slug, token
     */
    private final String identifierToSearchFor;

    protected FetchImpl(final JsonEndpoint<T> endpoint, final String identifierToSearchFor) {
        this.endpoint = endpoint;
        this.identifierToSearchFor = identifierToSearchFor;
    }

    @Override
    public Function<HttpResponse, Optional<T>> resultMapper() {
        return httpResponse -> {
            final Optional<T> result;
            if (httpResponse.getStatusCode() == NOT_FOUND_404) {
                result = Optional.empty();
            } else {
                result = Optional.of(resultMapperOf(typeReference()).apply(httpResponse));
            }
            return result;
        };
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        if (!endpoint.endpoint().startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final String queryParameters = additionalQueryParameters().toStringWithOptionalQuestionMark();
        final String path = endpoint.endpoint() + "/" + identifierToSearchFor + queryParameters;
        return HttpRequestIntent.of(HttpMethod.GET, path);
    }

    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }


    protected UrlQueryBuilder additionalQueryParameters() {
        return UrlQueryBuilder.of();
    }

    @Override
    public boolean canDeserialize(final HttpResponse response) {
        return response.hasSuccessResponseCode() || response.getStatusCode() == NOT_FOUND_404;
    }
}
