package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;

public class ReferenceOptionalQueryModel<T, R> extends ReferenceQueryModelImpl<T, R> implements OptionalQueryModel<T> {
    public ReferenceOptionalQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Referenceable<R> reference) {
        return super.is(reference);
    }

    @Override
    public QueryPredicate<T> isIn(final List<? extends Referenceable<R>> elements) {
        return super.isIn(elements);
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return new OptionalQueryPredicate<>(this, true);
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return new OptionalQueryPredicate<>(this, false);
    }
}
