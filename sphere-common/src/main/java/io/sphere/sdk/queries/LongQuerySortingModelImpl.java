package io.sphere.sdk.queries;

import java.util.Optional;

public class LongQuerySortingModelImpl<T> extends IntegerLikeQuerySortingModel<T, Long> implements LongQuerySortingModel<T> {
    public LongQuerySortingModelImpl(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }
}
