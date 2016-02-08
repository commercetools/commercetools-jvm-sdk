package io.sphere.sdk.search;

/**
 * Range facets calculate statistical data (i.e. minimum, maximum, mean, count...) for all values of an attribute within a range.
 * Example: variants.price:range(0 to *)
 * @param <T> Type of the resource for the facet
 *
 * @see io.sphere.sdk.search.model.RangeTermFacetSearchModel
 */
public interface RangeFacetExpression<T> extends FacetExpression<T> {

    static <T> RangeFacetExpression<T> of(final String sphereFacetExpression) {
        return new SimpleRangeFacetExpression<>(sphereFacetExpression);
    }
}
