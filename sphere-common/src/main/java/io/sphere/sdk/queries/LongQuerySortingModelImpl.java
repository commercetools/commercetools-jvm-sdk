package io.sphere.sdk.queries;

import javax.annotation.Nullable;

public class LongQuerySortingModelImpl<T> extends IntegerLikeQuerySortingModel<T, Long> implements LongQuerySortingModel<T> {
    public LongQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
