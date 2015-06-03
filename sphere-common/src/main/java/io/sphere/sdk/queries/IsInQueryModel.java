package io.sphere.sdk.queries;

public interface IsInQueryModel<T, V> {
    @SuppressWarnings("unchecked")
    QueryPredicate<T> isIn(final V arg0, final V ... args);

    QueryPredicate<T> isIn(final Iterable<V> args);
}
