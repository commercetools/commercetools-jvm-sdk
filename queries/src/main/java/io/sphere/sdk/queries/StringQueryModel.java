package io.sphere.sdk.queries;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public class StringQueryModel<T> extends QueryModelImpl<T> {
    StringQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> is(final String s) {
        return new EqPredicate<>(this, s);
    }

    public Predicate<T> isNot(final String s) {
        return new NotEqPredicate<>(this, s);
    }

    public Predicate<T> isOneOf(final String arg0, final String ... args) {
        return new IsInPredicate<>(this, ImmutableList.<String>builder().add(arg0).add(args).build());
    }
}