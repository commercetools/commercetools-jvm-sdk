package io.sphere.sdk.queries;

public interface InequalityQueryModel<T, V> {
    QueryPredicate<T> isGreaterThan(final V value);

    QueryPredicate<T> isLessThan(final V value);

    QueryPredicate<T> isLessThanOrEqualTo(final V value);

    QueryPredicate<T> isGreaterThanOrEqualTo(final V value);
}
