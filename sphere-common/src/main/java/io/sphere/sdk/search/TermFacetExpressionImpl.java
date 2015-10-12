package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Collections.emptyList;

class TermFacetExpressionImpl<T, V> extends TermExpression<T, V> implements TermFacetExpression<T> {

    TermFacetExpressionImpl(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, @Nullable final String alias) {
        super(searchModel, typeSerializer, emptyList(), alias);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && expression().equals(((FacetExpression) o).expression());
    }
}
