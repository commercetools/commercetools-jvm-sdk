package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

public interface SphereEnumerationQueryModel<T, E extends SphereEnumeration> extends EqualityQueryModel<T, E>,
        NotEqualQueryModel<T, E>, IsInQueryModel<T, E>, IsNotInQueryModel<T, E> {
    @Override
    QueryPredicate<T> is(final E value);

    @Override
    QueryPredicate<T> isIn(final Iterable<E> args);

    @Override
    QueryPredicate<T> isNotIn(final Iterable<E> args);

    @Override
    QueryPredicate<T> isNot(final E element);
}
