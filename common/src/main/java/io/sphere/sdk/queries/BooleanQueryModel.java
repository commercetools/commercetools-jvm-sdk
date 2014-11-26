package io.sphere.sdk.queries;

import java.util.Optional;

public class BooleanQueryModel<T> extends QueryModelImpl<T> {

    public BooleanQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> is(final boolean value) {
        return new EqPredicate<>(this, value);
    }
}
