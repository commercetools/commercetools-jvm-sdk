package io.sphere.sdk.queries;

import javax.annotation.Nullable;

public class IntegerQuerySortingModelImpl<T> extends IntegerLikeQuerySortingModel<T, Integer> implements IntegerQuerySortingModel<T> {
    public IntegerQuerySortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }
}
