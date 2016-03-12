package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

public final class ReferenceFilterSearchModel<T> extends SearchModelImpl<T> implements ExistsAndMissingFilterSearchModelSupport<T> {

    ReferenceFilterSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
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
