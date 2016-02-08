package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class DoubleQuerySortingModelImpl<T> extends NumberLikeQuerySortingModelImpl<T, Double> implements DoubleQuerySortingModel<T> {
    public DoubleQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
