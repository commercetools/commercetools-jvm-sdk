package io.sphere.sdk.queries;

import javax.annotation.Nullable;

final class LongQuerySortingModelImpl<T> extends NumberLikeQuerySortingModelImpl<T, Long> implements LongQuerySortingModel<T> {
    public LongQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
