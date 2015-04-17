package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.client.SphereRequestBase;
import io.sphere.sdk.http.HttpResponse;

import java.util.Optional;

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
    public Optional<T> deserialize(final HttpResponse httpResponse) {
        return Optional.of(httpResponse)
                .filter(r -> r.getStatusCode() != NOT_FOUND_404)
                .flatMap(r -> resultMapperOf(pagedQueryResultTypeReference).apply(httpResponse).head());
    }

    @Override
    public boolean canDeserialize(final HttpResponse httpResponse) {
        return httpResponse.hasSuccessResponseCode() || httpResponse.getStatusCode() == NOT_FOUND_404;
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        return query.httpRequestIntent();
    }
}
