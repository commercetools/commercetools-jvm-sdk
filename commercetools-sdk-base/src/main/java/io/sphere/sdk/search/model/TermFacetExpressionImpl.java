package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

import static java.util.Collections.emptyList;

class TermFacetExpressionImpl<T, V> extends TermExpression<T, V> implements TermFacetExpression<T> {

    TermFacetExpressionImpl(final SearchModel<T> searchModel, final Function<V, String> typeSerializer, @Nullable final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, emptyList(), alias, isCountingProducts);
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FacetExpression && expression().equals(((FacetExpression) o).expression());
    }

    @Nullable
    @Override
    public String alias() {
        return super.alias();
    }

    @Override
    public Boolean isCountingProducts() {
        return super.isCountingProducts();
    }

    @Nullable
    @Override
    public String value() {
        return null;
    }
}
