package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Optional;
import io.sphere.sdk.client.HttpRequest;

import java.util.List;

public abstract class EntityQueryWithCopyWrapper<I, R, M> implements EntityQueryWithCopy<I, R, M> {

    protected abstract EntityQueryWithCopy<I, R, M> delegate();

    public EntityQueryWithCopy<I, R, M> withPredicate(Predicate<M> predicate) {
        return delegate().withPredicate(predicate);
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withSort(List<Sort> sort) {
        return delegate().withSort(sort);
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withLimit(long limit) {
        return delegate().withLimit(limit);
    }

    @Override
    public EntityQueryWithCopy<I, R, M> withOffset(long limit) {
        return delegate().withOffset(limit);
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
    public TypeReference<PagedQueryResult<R>> typeReference() {
        return delegate().typeReference();
    }

    @Override
    public HttpRequest httpRequest() {
        return delegate().httpRequest();
    }
}
