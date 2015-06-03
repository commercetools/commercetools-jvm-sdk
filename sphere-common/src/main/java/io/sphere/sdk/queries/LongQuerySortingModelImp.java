package io.sphere.sdk.queries;

import java.util.Optional;

public class LongQuerySortingModelImp<T> extends IntegerLikeQuerySortingModel<T, Long> implements LongQuerySortingModel<T> {
    public LongQuerySortingModelImp(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }
}
