package io.sphere.internal.request;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;

/** Request that fetches a single object, implemented using a query endpoint.
 * Used when fetching products by slug. */
public class FetchRequestBasedOnQuery<T> implements FetchRequest<T>  {
    private QueryRequest<T> underlyingQueryRequest;

    public FetchRequestBasedOnQuery(QueryRequest<T> underlyingQueryRequest) {
        this.underlyingQueryRequest = underlyingQueryRequest;
    }

    @Override public Optional<T> fetch() {
        return Util.sync(fetchAsync());
    }

    @Override public ListenableFuture<Optional<T>> fetchAsync() {
        return Futures.transform(this.underlyingQueryRequest.fetchAsync(), new Function<QueryResult<T>, Optional<T>>() {
            public Optional<T> apply(QueryResult<T> result) {
                assert result != null;
                if (result.getCount() == 0) return Optional.absent();
                if (result.getCount() > 1) {
                    Log.warn("Fetch query returned more than one object: " + FetchRequestBasedOnQuery.this.underlyingQueryRequest);
                }
                return Optional.of(result.getResults().get(0));
            }
        });
    }

    @Override public FetchRequest<T> expand(String... paths) {
        underlyingQueryRequest = this.underlyingQueryRequest.expand(paths);
        return this;
    }

    // testing purposes
    public QueryRequest<T> getUnderlyingQueryRequest() {
        return underlyingQueryRequest;
    }

    // logging and debugging purposes
    @Override public String toString() {
        return getUnderlyingQueryRequest().toString();
    }

    public static <T> FetchRequest<T> asFetchRequest(final QueryRequest<T> queryRequest) {
        return new FetchRequestBasedOnQuery<T>(queryRequest);
    }
}
