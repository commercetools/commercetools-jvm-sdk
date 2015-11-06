package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetAndFilterExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

public class TermFacetAndFilterSearchModel<T, V> extends FacetAndFilterSearchModel<T, V> {

    TermFacetAndFilterSearchModel(@Nullable final SearchModel<T> parent, final Function<V, String> typeSerializer) {
        super(parent, typeSerializer);
    }

    @Override
    public TermFacetAndFilterExpression<T> by(final V value) {
        return super.by(value);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAny(final Iterable<V> values) {
        return super.byAny(values);
    }

    @Override
    public TermFacetAndFilterExpression<T> byAll(final Iterable<V> values) {
        return super.byAll(values);
    }

}
