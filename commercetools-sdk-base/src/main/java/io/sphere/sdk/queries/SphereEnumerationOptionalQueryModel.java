package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

public interface SphereEnumerationOptionalQueryModel<T, E extends SphereEnumeration> extends SphereEnumerationQueryModel<T, E>,
        OptionalQueryModel<T> {
    @Override
    QueryPredicate<T> is(final E value);

    @Override
    QueryPredicate<T> isIn(final Iterable<E> args);

    @Override
    QueryPredicate<T> isNot(final E element);

    @Override
    QueryPredicate<T> isNotIn(final Iterable<E> args);

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();
}
