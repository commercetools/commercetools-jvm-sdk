package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;

import javax.annotation.Nullable;
import java.util.List;

final class ExistsAndMissingFilterSearchModelSupportImpl<T> extends SearchModelImpl<T> implements ExistsAndMissingFilterSearchModelSupport<T> {
    ExistsAndMissingFilterSearchModelSupportImpl(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
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
