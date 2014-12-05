package io.sphere.sdk.search;

class TermFilterExpression<T> extends TermExpression<T> implements FilterExpression<T> {

    public TermFilterExpression(final SearchModel<T> searchModel, final Iterable<String> terms) {
        super(searchModel, terms);
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
