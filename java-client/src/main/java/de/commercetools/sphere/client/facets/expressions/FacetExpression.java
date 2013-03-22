package io.sphere.client.facets.expressions;

import io.sphere.client.QueryParam;

import java.util.List;

/** Specifies an attribute to aggregate facet counts on when fetching products, such as 'variants.attributes.color'.
 *
 *  Note that by applying a terms, values or ranges facet, the result set itself is not restricted.
 *  To implement a typical faceting behavior, use multi-select facets. */
public interface FacetExpression {
    /** Sphere HTTP API query parameter that this facet expression will be turned into. Useful mostly for debugging purposes. */
    List<QueryParam> createQueryParams();
}
