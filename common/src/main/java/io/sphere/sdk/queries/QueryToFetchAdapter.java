package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.SphereClientRequestBase;
import io.sphere.sdk.http.HttpRequest;
import io.sphere.sdk.http.HttpResponse;

import java.util.Optional;
import java.util.function.Function;

/**
 * Provides a {@link io.sphere.sdk.queries.Fetch} interface implementation for queries which return 0 to 1 results.
 * @param <T> type of the resource to be loaded
 */
public abstract class QueryToFetchAdapter<T> extends SphereClientRequestBase implements Fetch<T> {
    private final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference;
    private final Query<T> query;

    protected QueryToFetchAdapter(final TypeReference<PagedQueryResult<T>> pagedQueryResultTypeReference, final Query<T> query) {
        this.pagedQueryResultTypeReference = pagedQueryResultTypeReference;
        this.query = query;
    }

    @Override
    public Function<HttpResponse, Optional<T>> resultMapper() {
        return httpResponse -> {
            final Optional<T> result;
            if (httpResponse.getStatusCode() == 404) {
                result = Optional.empty();
            } else {
                final PagedQueryResult<T> queryResult = resultMapperOf(pagedQueryResultTypeReference).apply(httpResponse);
                result = queryResult.head();
            }
            return result;
        };
    }

    @Override
    public boolean canHandleResponse(final HttpResponse response) {
        return response.hasSuccessResponseCode() || response.getStatusCode() == 404;
    }

    @Override
    public HttpRequest httpRequest() {
        return query.httpRequest();
    }
}
