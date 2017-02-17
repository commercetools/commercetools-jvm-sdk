package io.sphere.sdk.queries;

import io.sphere.sdk.models.Point;

class WithinQueryPredicate<T> extends QueryModelQueryPredicate<T> {
    private final Point center;
    private final Double radius;

    public WithinQueryPredicate(final QueryModel<T> queryModel, final Point center, final Double radius) {
        super(queryModel);
        this.center = center;
        this.radius = radius;
    }

    @Override
    protected String render() {
        return String.format("within circle(%d, %d, %d)", center.getLongitude(), center.getLatitude(), radius);
    }
}
