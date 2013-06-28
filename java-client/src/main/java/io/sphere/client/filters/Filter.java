package io.sphere.client.filters;

import io.sphere.client.filters.expressions.FilterExpression;

import java.util.Map;

/** Filter 'component' that supports construction of backend queries based on application's URL query string.
 *
 *  See {@link FilterParser} for reconstructing state of multiple filters from application's URL.
 * */
public interface Filter {
    /** Creates a backend query for this filter, based on current values in the application's query string. */
    FilterExpression parse(Map<String,String[]> queryString);

    /** Sets a custom query parameter name that will represent this filter in the application's query string. */
    Filter setQueryParam(String queryParam);
}
