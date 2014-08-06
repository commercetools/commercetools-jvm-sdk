package io.sphere.sdk.queries;

import java.util.Optional;

public class StringQuerySortingModel<T> extends StringQueryModel<T> implements SortingModel<T> {
    public StringQuerySortingModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Sort<T> sort(SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
    }
}