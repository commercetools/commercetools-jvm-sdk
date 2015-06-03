package io.sphere.sdk.queries;

public interface OptionalQueryModel<T> {
    QueryPredicate<T> isPresent();

    QueryPredicate<T> isNotPresent();
}
