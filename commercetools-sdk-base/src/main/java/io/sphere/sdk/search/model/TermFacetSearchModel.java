package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilteredFacetExpression;
import io.sphere.sdk.search.TermFacetExpression;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Model to build term facets.
 * @param <T> type of the resource
 * @param <V> type of the value
 */
public interface TermFacetSearchModel<T, V> extends FacetSearchModel<T, V> {

    @Override
    FilteredFacetExpression<T> onlyTerm(final V value);

    @Override
    FilteredFacetExpression<T> onlyTerm(final Iterable<V> values);

    @Override
    FilteredFacetExpression<T> onlyTermAsString(final Iterable<String> values);

    @Override
    TermFacetSearchModel<T, V> withAlias(final String alias);

    @Override
    TermFacetSearchModel<T, V> withCountingProducts(final Boolean isCountingProducts);

    /**
     * Creates an instance of the search model to generate term facet expressions.
     * @param attributePath the path of the attribute as expected by Commercetools Platform (e.g. "variants.attributes.color.key")
     * @param typeSerializer the function to convert the provided value to a string accepted by Commercetools Platform
     * @param <T> type of the resource
     * @param <V> type of the value
     * @return new instance of TermFacetSearchModel
     */
    static <T, V> TermFacetSearchModel<T, V> of(final String attributePath, final Function<V, String> typeSerializer) {
        return new TermFacetSearchModelImpl<>(new SearchModelImpl<>(attributePath), typeSerializer);
    }
}
