package io.sphere.sdk.queries;

public interface IsInQueryModel<T, V> {

    QueryPredicate<T> isIn(final Iterable<V> args);
}
