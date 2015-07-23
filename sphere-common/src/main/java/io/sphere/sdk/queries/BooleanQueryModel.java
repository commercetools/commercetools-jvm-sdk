package io.sphere.sdk.queries;

import java.util.Objects;

public class BooleanQueryModel<T> extends QueryModelImpl<T> implements EqualityQueryModel<T, Boolean> {

    public BooleanQueryModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> is(final Boolean value) {
        return isPredicate(Objects.requireNonNull(value));
    }
}
