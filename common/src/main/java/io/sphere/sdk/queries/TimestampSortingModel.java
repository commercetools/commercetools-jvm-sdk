package io.sphere.sdk.queries;

import java.util.Optional;

public class TimestampSortingModel<T> extends QueryModelImpl<T> implements SortingModel<T> {
    public TimestampSortingModel(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Sort<T> sort(final SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
    }
}
