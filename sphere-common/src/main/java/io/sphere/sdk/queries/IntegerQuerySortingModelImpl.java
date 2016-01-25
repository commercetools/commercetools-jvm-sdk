package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class IntegerQuerySortingModelImpl<T> extends NumberLikeQuerySortingModelImpl<T, Integer> implements IntegerQuerySortingModel<T> {
    public IntegerQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
