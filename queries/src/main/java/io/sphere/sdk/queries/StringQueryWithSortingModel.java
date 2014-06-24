package io.sphere.sdk.queries;

import com.google.common.base.Optional;

public class StringQueryWithSortingModel<T> extends StringQueryModel<T> implements SortingModel<T> {
    public StringQueryWithSortingModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Sort sort(SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
    }
}