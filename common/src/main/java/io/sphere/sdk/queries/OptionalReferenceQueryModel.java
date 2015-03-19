package io.sphere.sdk.queries;

import io.sphere.sdk.models.Referenceable;

import java.util.List;
import java.util.Optional;

public class OptionalReferenceQueryModel<T, R> extends ReferenceQueryModel<T, R> {
    public OptionalReferenceQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Predicate<T> is(final Referenceable<R> reference) {
        return super.is(reference);
    }

    @Override
    public Predicate<T> isAnyOf(final List<? extends Referenceable<R>> elements) {
        return super.isAnyOf(elements);
    }

    public Predicate<T> isPresent() {
        return new OptionalPredicate<>(this, true);
    }

    public Predicate<T> isNotPresent() {
        return new OptionalPredicate<>(this, false);
    }
}
