package io.sphere.sdk.queries;

public class TimestampSortingModel<T> extends QueryModelImpl<T> implements QuerySortingModel<T> {
    public TimestampSortingModel(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QuerySort<T> sort(final QuerySortDirection sortDirection) {
        return new SphereQuerySort<>(this, sortDirection);
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }
}
