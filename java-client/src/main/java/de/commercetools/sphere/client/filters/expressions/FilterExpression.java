package io.sphere.client.filters.expressions;

import io.sphere.client.QueryParam;

import java.util.List;

/** Represents a query constraint when fetching products, such as 'attributes.color == "green"'. */
public interface FilterExpression {
    /** Sphere HTTP API query parameter that this filter will be turned into. Useful mostly for debugging purposes. */
    List<QueryParam> createQueryParams();
}
