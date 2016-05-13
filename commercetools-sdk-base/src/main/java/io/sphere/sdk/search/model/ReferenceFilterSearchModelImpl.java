package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

final class ReferenceFilterSearchModelImpl<T> extends SearchModelImpl<T> implements ReferenceFilterSearchModel<T> {

    protected ReferenceFilterSearchModelImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").filtered();
    }

    @Override
    public List<FilterExpression<T>> exists() {
        return existsFilters();
    }

    @Override
    public List<FilterExpression<T>> missing() {
        return missingFilters();
    }
}
