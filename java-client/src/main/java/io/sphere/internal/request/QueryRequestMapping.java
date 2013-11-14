package io.sphere.internal.request;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.internal.util.Util;

import java.util.List;

/**
 * Converts a {@code QueryRequest<A>} in a {@code QueryRequest<B>}.
 */
public abstract class QueryRequestMapping<A, B> implements QueryRequest<B> {
    private final QueryRequest<A> delegate;

    protected QueryRequestMapping(QueryRequest<A> delegate) {
        this.delegate = delegate;
    }

    @Override
    public final QueryResult<B> fetch() {
        return Util.sync(fetchAsync());
    }

    @Override
    public ListenableFuture<QueryResult<B>> fetchAsync() {
        return Futures.transform(delegate.fetchAsync(), new Function<QueryResult<A>, QueryResult<B>>() {
            @Override
            public QueryResult<B> apply(QueryResult<A> a) {
                List<B> bList = Lists.transform(a.getResults(), new Function<A, B>() {
                    @Override
                    public B apply(final A a) {
                        return QueryRequestMapping.this.convert(a);
                    }
                });
                return new QueryResult<B>(a.getOffset(), a.getCount(), a.getTotal(), bList);
            }
        });
    }

    /**
     * Override this method to convert elements from A to B.
     *
     * @param a The single element that should be converted to B.
     * @return The converted a as B.
     */
    protected abstract B convert(A a);

    @Override
    public QueryRequest<B> where(String predicate) {
        delegate.where(predicate);
        return this;
    }

    @Override
    public QueryRequest<B> page(int page) {
        delegate.page(page);
        return this;
    }

    @Override
    public QueryRequest<B> pageSize(int pageSize) {
        delegate.pageSize(pageSize);
        return this;
    }

    @Override
    public QueryRequest<B> expand(String... paths) {
        delegate.expand(paths);
        return this;
    }
}
