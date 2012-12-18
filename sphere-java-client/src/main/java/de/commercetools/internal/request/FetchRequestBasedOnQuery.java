package de.commercetools.internal.request;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.model.QueryResult;

/**
 * Request that fetches a single object, implemented using a query endpoint.
 * Used when fetching products by slug. */
public class FetchRequestBasedOnQuery<T> implements FetchRequest<T> {
    private QueryRequest<T> underlyingQueryRequest;

    public FetchRequestBasedOnQuery(QueryRequest<T> underlyingQueryRequest) {
        this.underlyingQueryRequest = underlyingQueryRequest;
    }

    @Override public Optional<T> fetch() {
        try {
            return fetchAsync().get();
        } catch(Exception ex) {
            throw new SphereException(ex);
        }
    }

    @Override public ListenableFuture<Optional<T>> fetchAsync() {
        return Futures.transform(this.underlyingQueryRequest.fetchAsync(), new Function<QueryResult<T>, Optional<T>>() {
            public Optional<T> apply(QueryResult<T> result) {
                assert result != null;
                if (result.getCount() == 0) return Optional.absent();
                if (result.getCount() > 1) {
                    Log.warn("Fetch query returned more than one object: " + FetchRequestBasedOnQuery.this.underlyingQueryRequest.getUrl());
                }
                return Optional.of(result.getResults().get(0));
            }
        });
    }

    @Override public FetchRequest<T> expand(String... paths) {
        this.underlyingQueryRequest = this.underlyingQueryRequest.expand(paths);
        return this;
    }

    @Override public String getUrl() {
        return this.underlyingQueryRequest.getUrl();
    }
}
