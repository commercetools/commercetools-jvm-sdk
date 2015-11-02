package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class TimestampSortingModelImpl<T> extends QueryModelImpl<T> implements TimestampSortingModel<T> {
    public TimestampSortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Deprecated
    @Override
    public QuerySort<T> sort(final QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }
}
