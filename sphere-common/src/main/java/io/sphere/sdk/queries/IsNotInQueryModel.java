package io.sphere.sdk.queries;

public interface IsNotInQueryModel<T, V> {
    @SuppressWarnings("unchecked")
    QueryPredicate<T> isNotIn(final V arg0, final V... args);

    QueryPredicate<T> isNotIn(final Iterable<V> args);
}
