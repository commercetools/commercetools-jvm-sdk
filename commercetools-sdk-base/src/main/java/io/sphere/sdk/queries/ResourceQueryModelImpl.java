package io.sphere.sdk.queries;

import io.sphere.sdk.models.Identifiable;

public abstract class ResourceQueryModelImpl<T> extends QueryModelImpl<T> implements ResourceQueryModel<T> {
    protected ResourceQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public final StringQuerySortingModel<T> id() {
        return stringModel("id");
    }

    @Override
    public QueryPredicate<T> is(final Identifiable<T> identifiable) {
        return id().is(identifiable.getId());
    }

    @Override
    public final TimestampSortingModel<T> createdAt() {
        return new TimestampSortingModelImpl<>(this, "createdAt");
    }

    @Override
    public final TimestampSortingModel<T> lastModifiedAt() {
        return new TimestampSortingModelImpl<>(this, "lastModifiedAt");
    }

    @Override
    public final QueryPredicate<T> not(final QueryPredicate<T> queryPredicateToNegate) {
        return queryPredicateToNegate.negate();
    }
}
