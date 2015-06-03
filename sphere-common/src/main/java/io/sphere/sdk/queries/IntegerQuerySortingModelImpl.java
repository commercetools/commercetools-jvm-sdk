package io.sphere.sdk.queries;

import java.util.Optional;

final class IntegerQuerySortingModelImpl<T> extends IntegerLikeQuerySortingModel<T, Integer> implements IntegerQuerySortingModel<T> {
    public IntegerQuerySortingModelImpl(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }
}
