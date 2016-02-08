package io.sphere.sdk.queries;

public interface IsNotInQueryModel<T, V> {

    QueryPredicate<T> isNotIn(final Iterable<V> args);
}
