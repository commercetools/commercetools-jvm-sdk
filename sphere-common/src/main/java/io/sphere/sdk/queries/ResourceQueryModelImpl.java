package io.sphere.sdk.queries;

public class ResourceQueryModelImpl<T> extends QueryModelImpl<T> implements ResourceQueryModel<T> {
    protected ResourceQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public final StringQuerySortingModel<T> id() {
        return stringModel("id");
    }

    @Override
    public final TimestampSortingModel<T> createdAt() {
        return new TimestampSortingModelImpl<>(this, "createdAt");
    }

    @Override
    public final TimestampSortingModel<T> lastModifiedAt() {
        return new TimestampSortingModelImpl<>(this, "lastModifiedAt");
    }

    public final QueryPredicate<T> not(final QueryPredicate<T> queryPredicateToNegate) {
        return queryPredicateToNegate.negate();
    }
}
