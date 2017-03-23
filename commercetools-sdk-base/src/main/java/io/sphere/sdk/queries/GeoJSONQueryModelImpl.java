package io.sphere.sdk.queries;

import io.sphere.sdk.models.Point;

import javax.annotation.Nullable;

class GeoJSONQueryModelImpl<T> extends QueryModelImpl<T> implements GeoJSONQueryModel<T> {

    GeoJSONQueryModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> withinCircle(final Point center, final Double radius) {
        return withinCirclePredicate(center, radius);
    }
}
