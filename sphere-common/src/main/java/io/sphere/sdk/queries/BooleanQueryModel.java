package io.sphere.sdk.queries;

import java.util.Objects;
import java.util.Optional;

public class BooleanQueryModel<T> extends QueryModelImpl<T> implements EqualityQueryModel<T, Boolean> {

    public BooleanQueryModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Boolean value) {
        return isPredicate(Objects.requireNonNull(value));
    }
}
