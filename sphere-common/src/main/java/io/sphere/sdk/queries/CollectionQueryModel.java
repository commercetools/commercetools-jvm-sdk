package io.sphere.sdk.queries;

public interface CollectionQueryModel<T> {
    QueryPredicate<T> isEmpty();

    QueryPredicate<T> isNotEmpty();
}
