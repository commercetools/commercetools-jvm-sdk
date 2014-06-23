package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.client.HttpResponse;

import java.util.List;

public abstract class QueryDslWrapper<I, R extends I, M> implements QueryDsl<I, R, M> {

    protected abstract QueryDsl<I, R, M> delegate();

    public QueryDsl<I, R, M> withPredicate(Predicate<M> predicate) {
        return delegate().withPredicate(predicate);
    }

    @Override
    public QueryDsl<I, R, M> withSort(List<Sort> sort) {
        return delegate().withSort(sort);
    }

    @Override
    public QueryDsl<I, R, M> withLimit(long limit) {
        return delegate().withLimit(limit);
    }

    @Override
    public QueryDsl<I, R, M> withOffset(long offset) {
        return delegate().withOffset(offset);
    }

    @Override
    public TypeReference<PagedQueryResult<R>> typeReference() {
        return delegate().typeReference();
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
