package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;

public interface ReferenceQueryModel<T, R> extends ReferenceQueryModelLike<T>, EqualityQueryModel<T, Referenceable<R>> {
    @Override
    QueryPredicate<T> is(Referenceable<R> reference);

    QueryPredicate<T> isIn(List<? extends Referenceable<R>> elements);

    @Override
    StringQueryModel<T> id();

    @Override
    QueryPredicate<T> isInIds(List<String> ids);

    @Override
    StringQueryModel<T> typeId();
}
