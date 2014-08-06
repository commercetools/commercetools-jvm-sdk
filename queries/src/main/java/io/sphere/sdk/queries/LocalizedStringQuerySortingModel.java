package io.sphere.sdk.queries;

import java.util.Optional;

public class LocalizedStringQuerySortingModel<T> extends LocalizedStringQueryModel<T> implements SortingModel<T> {
    public LocalizedStringQuerySortingModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Sort<T> sort(SortDirection sortDirection) {
        return new SphereSort<>(this, sortDirection);
    }
}