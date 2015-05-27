package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;
import java.util.Optional;

public class OptionalReferenceQueryModel<T, R> extends ReferenceQueryModel<T, R> {
    public OptionalReferenceQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
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

    public QueryPredicate<T> isPresent() {
        return new OptionalQueryPredicate<>(this, true);
    }

    public QueryPredicate<T> isNotPresent() {
        return new OptionalQueryPredicate<>(this, false);
    }
}
