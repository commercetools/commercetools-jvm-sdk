package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FacetExpression;
import io.sphere.sdk.search.FilteredFacetExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.requireNonEmpty;

class FilteredFacetExpressionImpl<T, V> extends TermExpression<T, V> implements FilteredFacetExpression<T> {

    FilteredFacetExpressionImpl(final SearchModel<T> searchModel, final Function<V, String> typeSerializer,
                                final Iterable<V> terms, @Nullable final String alias, final Boolean isCountingProducts) {
        super(searchModel, typeSerializer, requireNonEmpty(terms), alias, isCountingProducts);
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

}
