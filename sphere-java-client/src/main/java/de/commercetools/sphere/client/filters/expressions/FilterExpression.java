package de.commercetools.sphere.client.filters.expressions;

import de.commercetools.sphere.client.QueryParam;

import java.util.List;

/** Represents a query constraint when fetching products, such as 'attributes.color == "green"'. */
public interface FilterExpression {
    /** Sphere HTTP API query parameter that this filter will be turned into. Useful mostly for debugging purposes. */
    List<QueryParam> createQueryParams();
}
