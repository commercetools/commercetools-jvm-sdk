package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.http.*;
import io.sphere.sdk.utils.UrlQueryBuilder;

import java.util.Optional;
import java.util.function.Function;

public abstract class FetchImpl<T> extends ClientRequestBase implements Fetch<T> {

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
            if (httpResponse.getStatusCode() == 404) {
                result = Optional.empty();
            } else {
                result = Optional.of(resultMapperOf(typeReference()).apply(httpResponse));
            }
            return result;
        };
    }

    @Override
    public HttpRequest httpRequest() {
        if (!endpoint.endpoint().startsWith("/")) {
            throw new RuntimeException("By convention the paths start with a slash, see baseEndpointWithoutId()");
        }
        final String queryParameters = additionalQueryParameters().toStringWithOptionalQuestionMark();
        final String path = endpoint.endpoint() + "/" + identifierToSearchFor + queryParameters;
        return HttpRequest.of(HttpMethod.GET, path);
    }

    protected TypeReference<T> typeReference() {
        return endpoint.typeReference();
    }


    protected UrlQueryBuilder additionalQueryParameters() {
        return UrlQueryBuilder.of();
    }

    @Override
    public boolean canHandleResponse(final HttpResponse response) {
        return response.hasSuccessResponseCode() || response.getStatusCode() == 404;
    }
}
