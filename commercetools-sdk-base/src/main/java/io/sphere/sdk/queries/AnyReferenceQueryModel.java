package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;

public interface AnyReferenceQueryModel<T> extends ReferenceQueryModelLike<T> {
    <R> QueryPredicate<T> is(Referenceable<R> reference);

    <R> QueryPredicate<T> isIn(List<? extends Referenceable<R>> elements);


    @Override
    StringQueryModel<T> id();

    @Override
    QueryPredicate<T> isInIds(List<String> ids);

    @Override
    StringQueryModel<T> typeId();
}
