package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

public interface ReferenceCollectionQueryModel<T, R> extends CollectionQueryModel<T> {
    QueryPredicate<T> isIn(Iterable<? extends Referenceable<R>> references);

    StringQueryModel<T> id();
}
