package io.sphere.sdk.queries;

public interface StringCollectionQueryModel<T> extends CollectionQueryModel<T> {
    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    QueryPredicate<T> containsAll(final Iterable<String> items);

    QueryPredicate<T> containsAny(final Iterable<String> items);
}
