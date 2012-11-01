package de.commercetools.internal.request;

import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.shop.model.Product;

/** Creates instances of Product requests. Allows for mocking in tests.
 *  Converts products from the raw {@link de.commercetools.sphere.client.model.products.BackendProduct} to {@link Product}. */
public interface ProductRequestFactory {
    /** Creates a request to a query endpoint that parses the response into a given type. */
    QueryRequest<Product> createQueryRequest(String url);

    /** Creates a search request that parses the response into a given type. */
    SearchRequest<Product> createSearchRequest(String url, Iterable<FilterExpression> filters);

    // no product modifications yet
    // createCommandRequest
}
