package io.sphere.sdk.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.models.Base;

import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class ResourceMetaModelQueryDslBuilderImpl<B, T, C extends MetaModelQueryDsl<T, C, Q, E>, Q, E> extends Base implements ResourceMetaModelQueryDslBuilder<B, T, C, Q, E> {
    private C delegate;

    protected ResourceMetaModelQueryDslBuilderImpl(final C initialQuery) {
        this.delegate = initialQuery;
    }

    @Override
    public B plusPredicates(final Function<Q, QueryPredicate<T>> m) {
        return op(d -> d.plusPredicates(m));
    }

    private B op(final UnaryOperator<C> op) {
        this.delegate = op.apply(this.delegate);
        return getThis();
    }

    protected abstract B getThis();

    @Override
    public B plusPredicates(final QueryPredicate<T> queryPredicate) {
        return op(d -> d.plusPredicates(queryPredicate));
    }

    @Override
    public B plusPredicates(final String queryPredicate) {
        return op(d -> d.plusPredicates(queryPredicate));
    }

    @Override
    public B plusPredicates(final List<QueryPredicate<T>> queryPredicates) {
        return op(d -> d.plusPredicates(queryPredicates));
    }

    @Override
    public B plusSort(final Function<Q, QuerySort<T>> m) {
        return op(d -> d.plusSort(m));
    }

    @Override
    public B plusSort(final List<QuerySort<T>> sort) {
        return op(d -> d.plusSort(sort));
    }

    @Override
    public B plusSort(final QuerySort<T> sort) {
        return op(d -> d.plusSort(sort));
    }

    @Override
    public B plusSort(final String sort) {
        return op(d -> d.plusSort(sort));
    }

    @Override
    public B predicates(final Function<Q, QueryPredicate<T>> m) {
        return op(d -> d.withPredicates(m));
    }

    @Override
    public B predicates(final QueryPredicate<T> queryPredicate) {
        return op(d -> d.withPredicates(queryPredicate));
    }

    @Override
    public B predicates(final String queryPredicate) {
        return op(d -> d.withPredicates(queryPredicate));
    }

    @Override
    public B predicates(final List<QueryPredicate<T>> queryPredicates) {
        return op(d -> d.withPredicates(queryPredicates));
    }

    @Override
    public B sort(final Function<Q, QuerySort<T>> m) {
        return op(d -> d.withSort(m));
    }

    @Override
    public B sort(final List<QuerySort<T>> sort) {
        return op(d -> d.withSort(sort));
    }

    @Override
    public B sort(final QuerySort<T> sort) {
        return op(d -> d.withSort(sort));
    }

    @Override
    public B sort(final String sort) {
        return op(d -> d.withSort(sort));
    }

    @Override
    public B sortMulti(final Function<Q, List<QuerySort<T>>> m) {
        return op(d -> d.withSortMulti(m));
    }

    @Override
    public B plusExpansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return op(d -> d.plusExpansionPaths(m));
    }

    @Override
    public B plusExpansionPaths(final String m) {
        return op(d -> d.plusExpansionPaths(m));
    }

    @Override
    public B expansionPaths(final Function<E, ExpansionPathContainer<T>> m) {
        return op(d -> d.withExpansionPaths(m));
    }

    @Override
    public B expansionPaths(final String m) {
        return op(d -> d.withExpansionPaths(m));
    }

    @Override
    public B fetchTotal(final boolean fetchTotal) {
        return op(d -> d.withFetchTotal(fetchTotal));
    }

    @Override
    public B limit(final Long limit) {
        return op(d -> d.withLimit(limit));
    }

    @Override
    public B limit(final long limit) {
        return op(d -> d.withLimit(limit));
    }

    @Override
    public B offset(final Long offset) {
        return op(d -> d.withOffset(offset));
    }

    @Override
    public B offset(final long offset) {
        return op(d -> d.withOffset(offset));
    }

    @Override
    public C build() {
        return delegate;
    }
}
