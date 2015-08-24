package io.sphere.sdk.search;

class TermFilterExpression<T, V> extends TermExpression<T, V> implements FilterExpression<T> {

    TermFilterExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<V> terms) {
        super(searchModel, typeSerializer, terms, null);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && toSearchExpression().equals(((FilterExpression) o).toSearchExpression());
    }
}
