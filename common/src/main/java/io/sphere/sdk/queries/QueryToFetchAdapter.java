package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;

import java.util.Optional;
import java.util.function.Function;

import static io.sphere.sdk.http.HttpStatusCode.NOT_FOUND_404;

/**
 * Provides a {@link io.sphere.sdk.queries.Fetch} interface implementation for queries which return 0 to 1 results.
 * @param <T> type of the resource to be loaded
 */
public abstract class QueryToFetchAdapter<T> extends SphereRequestBase implements Fetch<T> {
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
            if (httpResponse.getStatusCode() == NOT_FOUND_404) {
                result = Optional.empty();
            } else {
                final PagedQueryResult<T> queryResult = resultMapperOf(pagedQueryResultTypeReference).apply(httpResponse);
                result = queryResult.head();
            }
            return result;
        };
    }

    @Override
    public boolean canDeserialize(final HttpResponse response) {
        return response.hasSuccessResponseCode() || response.getStatusCode() == NOT_FOUND_404;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return query.httpRequestIntent();
    }
}
