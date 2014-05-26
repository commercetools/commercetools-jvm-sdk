package io.sphere.sdk.queries;

import com.google.common.base.Optional;

public class StringQueryModel<T> extends QueryModelImpl<T> {
    protected StringQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> is(String s) {
        return new EqPredicate<>(this, s);
    }
}