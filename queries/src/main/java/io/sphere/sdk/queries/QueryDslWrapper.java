package io.sphere.sdk.queries;

import com.google.common.base.Function;
import java.util.Optional;
import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.requests.HttpResponse;

import java.util.List;

public abstract class QueryDslWrapper<I, M> implements QueryDsl<I, M> {

    protected abstract QueryDsl<I, M> delegate();

    public QueryDsl<I, M> withPredicate(Predicate<M> predicate) {
        return delegate().withPredicate(predicate);
    }

    @Override
    public QueryDsl<I, M> withSort(List<Sort> sort) {
        return delegate().withSort(sort);
    }

    @Override
    public QueryDsl<I, M> withLimit(long limit) {
        return delegate().withLimit(limit);
    }

    @Override
    public QueryDsl<I, M> withOffset(long offset) {
        return delegate().withOffset(offset);
    }

    @Override
    public Optional<Predicate<M>> predicate() {
        return delegate().predicate();
    }

    @Override
    public List<Sort> sort() {
        return delegate().sort();
    }

    @Override
    public Optional<Long> limit() {
        return delegate().limit();
    }

    @Override
    public Optional<Long> offset() {
        return delegate().offset();
    }

    @Override
    public String endpoint() {
        return delegate().endpoint();
    }

    @Override
    public Function<HttpResponse, PagedQueryResult<I>> resultMapper() {
        return delegate().resultMapper();
    }

    @Override
    public HttpRequest httpRequest() {
        return delegate().httpRequest();
    }
}
