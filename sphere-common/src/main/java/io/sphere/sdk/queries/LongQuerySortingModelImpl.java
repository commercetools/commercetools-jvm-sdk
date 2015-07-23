package io.sphere.sdk.queries;

public class LongQuerySortingModelImpl<T> extends IntegerLikeQuerySortingModel<T, Long> implements LongQuerySortingModel<T> {
    public LongQuerySortingModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }
}
