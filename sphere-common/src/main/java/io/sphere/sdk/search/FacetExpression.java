package io.sphere.sdk.search;

import javax.annotation.Nullable;

/**
 * Facets calculate statistical counts to aid in faceted navigation.
 * Example: variants.attributes.color:"green","yellow"
 * @param <T> Type of the resource for the facet
 *
 * @see io.sphere.sdk.search.model.FacetSearchModel
 */
public interface FacetExpression<T> extends SearchExpression<T> {

    /**
     * Gets the path of the facet result, which is either the alias or the search path if no alias defined.
     * Example: variants.attributes.color
     * @return the path to access the facet result
     */
    String resultPath();

    /**
     * Gets the defined alias.
     * @return the alias of the facet
     */
    @Nullable
    String alias();
}
