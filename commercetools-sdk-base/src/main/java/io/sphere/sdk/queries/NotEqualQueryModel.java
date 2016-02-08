package io.sphere.sdk.queries;

public interface NotEqualQueryModel<T, E> {
    QueryPredicate<T> isNot(final E element);
}
