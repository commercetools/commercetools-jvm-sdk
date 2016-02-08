package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;

public interface ReferenceOptionalQueryModel<T, R> extends ReferenceQueryModel<T,R>, OptionalQueryModel<T> {
    @Override
    QueryPredicate<T> is(Referenceable<R> reference);

    @Override
    QueryPredicate<T> isIn(List<? extends Referenceable<R>> elements);

    @Override
    QueryPredicate<T> isPresent();

    @Override
    QueryPredicate<T> isNotPresent();
}
