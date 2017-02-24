package io.sphere.sdk.queries;

import io.sphere.sdk.models.Point;

/**
 * This predicate returns {@code true} if a {@link io.sphere.sdk.models.GeoJSON} object
 * is within the circle given by the center and radius in meters.
 *
 * @param <T> the type for which this predicate can be instantiated
 */
class WithinCircleQueryPredicate<T> extends QueryModelQueryPredicate<T> {
    private final Point center;
    private final Double radius;

    WithinCircleQueryPredicate(final QueryModel<T> queryModel, final Point center, final Double radius) {
        super(queryModel);
        this.center = center;
        this.radius = radius;
    }

    @Override
    protected String render() {
        return String.format(" within circle(%f, %f, %f)", center.getLongitude(), center.getLatitude(), radius);
    }
}
