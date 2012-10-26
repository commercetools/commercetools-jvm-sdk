package de.commercetools.sphere.client.facets.expressions;

import de.commercetools.sphere.client.QueryParam;

import java.util.List;

/** Specifies an attribute to aggregate facet counts on when fetching products, such as 'attributes.color'.
 *
 *  Note that by applying a terms, values or ranges facet, the result set is not restricted automatically.
 *  To aggregate facet counts as well as restrict result set, use multi-select facets, which essentially combine
 *  facet queries with additional filter queries.
 *  */
public interface FacetExpression {
    /** Sphere HTTP API query parameter that this facet expression will be turned into. Mostly for debugging purposes. */
    List<QueryParam> createQueryParams();
}
