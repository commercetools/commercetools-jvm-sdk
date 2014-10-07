package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.EqPredicate;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;

import java.util.Optional;

public class BooleanQueryModel<T> extends QueryModelImpl<T> {

    public BooleanQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<T> is(final boolean value) {
        return new EqPredicate<>(this, value);
    }
}
