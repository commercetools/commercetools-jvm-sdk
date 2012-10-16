package de.commercetools.sphere.client;

import java.util.Map;

/** Filter 'component' that supports construction of backend queries based on application's URL query string.
 *
 *  See {@link FilterParser} for reconstructing state of multiple filters from application's URL.
 * */
public interface FilterDefinition {
    /** Creates a backend facet query based on application's URL query parameters. */
    Filter parse(Map<String,String[]> queryString);
}
