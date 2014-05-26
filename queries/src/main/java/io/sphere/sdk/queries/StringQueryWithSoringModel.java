package io.sphere.sdk.queries;

import com.google.common.base.Optional;

public class StringQueryWithSoringModel<T> extends StringQueryModel<T> implements SortingModel<T> {
    protected StringQueryWithSoringModel(Optional<? extends QueryModel<T>> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public Sort<T> sort(SortDirection sortDirection) {
        return new SphereSort<T>(this, sortDirection);
    }
}