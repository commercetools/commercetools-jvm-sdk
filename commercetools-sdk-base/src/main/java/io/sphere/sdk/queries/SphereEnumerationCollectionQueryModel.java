package io.sphere.sdk.queries;

import io.sphere.sdk.models.SphereEnumeration;

public interface SphereEnumerationCollectionQueryModel<T, E extends SphereEnumeration> extends CollectionQueryModel<T> {
    @Override
    QueryPredicate<T> isEmpty();

    @Override
    QueryPredicate<T> isNotEmpty();

    QueryPredicate<T> containsAll(final Iterable<E> items);

    QueryPredicate<T> containsAny(final Iterable<E> items);
}
