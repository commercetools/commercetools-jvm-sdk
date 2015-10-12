package io.sphere.sdk.search;

import javax.annotation.Nullable;

class FilteredFacetExpressionImpl<T, V> extends TermExpression<T, V> implements FilteredFacetExpression<T> {

    FilteredFacetExpressionImpl(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, final Iterable<V> terms, @Nullable final String alias) {
        super(searchModel, typeSerializer, terms, alias);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && expression().equals(((FacetExpression) o).expression());
    }
}
