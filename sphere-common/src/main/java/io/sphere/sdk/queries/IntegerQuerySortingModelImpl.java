package io.sphere.sdk.queries;

public class IntegerQuerySortingModelImpl<T> extends IntegerLikeQuerySortingModel<T, Integer> implements IntegerQuerySortingModel<T> {
    public IntegerQuerySortingModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }
}
