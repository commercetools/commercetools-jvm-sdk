package io.sphere.sdk.search;

interface FacetExpressionBase<T> extends FacetExpression<T> {

    /**
     * Gets the path of the facet.
     * Example: variants.attributes.color
     * @return the path to access the facet.
     */
    abstract String searchPath();
}
