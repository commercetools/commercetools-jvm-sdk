package io.sphere.sdk.queries;

import java.util.Optional;

public class DefaultModelQueryModelImpl<T> extends QueryModelImpl<T> {
    protected DefaultModelQueryModelImpl(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public final StringQuerySortingModel<T> id() {
        return stringModel("id");
    }

    public final TimestampSortingModel<T> createdAt() {
        return new TimestampSortingModel<>(Optional.of(this), "createdAt");
    }

    public final TimestampSortingModel<T> lastModifiedAt() {
        return new TimestampSortingModel<>(Optional.of(this), "lastModifiedAt");
    }

    public final QueryPredicate<T> not(final QueryPredicate<T> queryPredicateToNegate) {
        return queryPredicateToNegate.negate();
    }
}
