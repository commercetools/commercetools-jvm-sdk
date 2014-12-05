package io.sphere.sdk.search;

class RangeFilterExpression<T> extends RangeExpression<T> implements FilterExpression<T> {

    RangeFilterExpression(final SearchModel<T> searchModel, final Iterable<String> ranges) {
        super(searchModel, ranges);
    }

    @Override
    public String toSphereFilter() {
        return toSphereSearchExpression();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && toSphereFilter().equals(((FilterExpression) o).toSphereFilter());
    }
}
