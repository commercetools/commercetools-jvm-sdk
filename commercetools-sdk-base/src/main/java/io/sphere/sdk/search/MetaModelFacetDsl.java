package io.sphere.sdk.search;

import java.util.function.Function;

public interface MetaModelFacetDsl<T, C, F> extends FacetDsl<T, C> {

    /**
     * Creates a new object with the properties of the old object but replaces all facets with a single facet expression to it by using meta models.
     * @param m function to use the meta model for facets to create a facet expression
     * @return new object
     */
    C withFacets(final Function<F, FacetExpression<T>> m);

    /**
     * Creates a new object with the properties of the old object but adds a new facet to it by using meta models.
     * @param m function to use the meta model for facets to create a facet expression
     * @return new object
     */
    C plusFacets(final Function<F, FacetExpression<T>> m);
}