package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

public final class ReferenceFilterSearchModel<T> extends SearchModelImpl<T> {

    ReferenceFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    public TermFilterSearchModel<T, String> id() {
        return new StringSearchModel<>(this, "id").filtered();
    }

    public List<FilterExpression<T>> exists() {
        return existsFilters();
    }

    public List<FilterExpression<T>> missing() {
        return missingFilters();
    }
}
