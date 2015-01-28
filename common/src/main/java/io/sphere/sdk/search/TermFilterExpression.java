package io.sphere.sdk.search;

class TermFilterExpression<T, V> extends TermExpression<T, V> implements FilterExpression<T> {

    TermFilterExpression(final SearchModel<T> searchModel, final Iterable<V> terms, final TypeSerializer<V> typeSerializer) {
        super(searchModel, terms, typeSerializer);
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
