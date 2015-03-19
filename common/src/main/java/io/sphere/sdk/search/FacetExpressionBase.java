package io.sphere.sdk.search;

interface FacetExpressionBase<T> extends FacetExpression<T> {

    /**
     * Gets the path of the facet result, which is either the alias or the search path if no alias defined.
     * Example: variants.attributes.color
     * @return the path to access the facet result.
     */
    String resultPath();
}
