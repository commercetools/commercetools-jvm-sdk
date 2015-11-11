package io.sphere.sdk.search.model;

import io.sphere.sdk.search.TermFacetAndFilterExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Model to build term facets and filters.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public class TermFacetAndFilterSearchModel<T, V> extends TermFacetAndFilterBaseSearchModel<T, V> {

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
