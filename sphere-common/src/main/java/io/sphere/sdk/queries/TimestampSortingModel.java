package io.sphere.sdk.queries;

import java.util.Optional;

public class TimestampSortingModel<T> extends QueryModelImpl<T> implements QuerySortingModel<T> {
    public TimestampSortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QuerySort<T> sort(final QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }

    @Override
    public IntermediateQuerySort<T> sort() {
        return new IntermediateQuerySort<>(this);
    }
}
