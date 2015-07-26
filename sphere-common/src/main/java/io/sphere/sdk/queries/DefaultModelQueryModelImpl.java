package io.sphere.sdk.queries;

public class DefaultModelQueryModelImpl<T> extends QueryModelImpl<T> {
    protected DefaultModelQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public final StringQuerySortingModel<T> id() {
        return stringModel("id");
    }

    public final TimestampSortingModel<T> createdAt() {
        return new TimestampSortingModel<>(this, "createdAt");
    }

    public final TimestampSortingModel<T> lastModifiedAt() {
        return new TimestampSortingModel<>(this, "lastModifiedAt");
    }

    public final QueryPredicate<T> not(final QueryPredicate<T> queryPredicateToNegate) {
        return queryPredicateToNegate.negate();
    }
}
