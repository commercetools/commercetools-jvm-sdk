package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import io.sphere.sdk.search.model.SearchModel;
import io.sphere.sdk.search.model.TermExpression;

import java.util.function.Function;

class TermFilterExpression<T, V> extends TermExpression<T, V> implements FilterExpression<T> {

    TermFilterExpression(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, final Iterable<V> terms) {
        super(searchModel, typeSerializer, terms, null);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FilterExpression && expression().equals(((FilterExpression) o).expression());
    }
}
