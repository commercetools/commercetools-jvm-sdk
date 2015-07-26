package io.sphere.sdk.queries;

import javax.annotation.Nullable;

public class TimestampSortingModel<T> extends QueryModelImpl<T> implements QuerySortingModel<T> {
    public TimestampSortingModel(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QuerySort<T> sort(final QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }
}
