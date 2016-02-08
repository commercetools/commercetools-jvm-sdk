package io.sphere.sdk.queries;

public interface EqualityQueryModel<T, E> {
    QueryPredicate<T> is(final E value);
}
