package io.sphere.sdk.search;

import javax.annotation.Nullable;

import static java.util.Collections.emptyList;

class TermFacetExpression<T, V> extends TermExpression<T, V> implements FacetExpression<T> {

    TermFacetExpression(final SearchModel<T> searchModel, final TypeSerializer<V> typeSerializer, @Nullable final String alias) {
        super(searchModel, typeSerializer, emptyList(), alias);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && expression().equals(((FacetExpression) o).expression());
    }
}
