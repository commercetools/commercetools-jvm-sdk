package io.sphere.sdk.queries;

import javax.annotation.Nullable;

import java.util.List;

final class StringCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements StringCollectionQueryModel<T> {
    public StringCollectionQueryModelImpl(@Nullable final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> containsAll(final Iterable<String> items) {
        final List<String> normalizedValues = normalizeIterable(items);
        return new ContainsAllPredicate<>(this, normalizedValues);
    }

    @Override
    public QueryPredicate<T> containsAny(final Iterable<String> items) {
        final List<String> normalizedValues = normalizeIterable(items);
        return new ContainsAnyPredicate<>(this, normalizedValues);
    }
}
