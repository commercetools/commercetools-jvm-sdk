package io.sphere.internal.request;

import io.sphere.client.FetchRequest;
import io.sphere.client.SearchRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.model.Product;

/** Creates instances of Product requests. Allows for mocking in tests.
 *  Converts products from the raw {@link io.sphere.client.model.products.BackendProduct} to {@link Product}. */
public interface ProductRequestFactory {
    /** Creates a request that fetches a single {@link Product}. */
    FetchRequest<Product> createFetchRequest(String url);

    /** Creates a request that fetches a single {@link Product}, implemented using a query endpoint. */
    FetchRequest<Product> createFetchRequestBasedOnQuery(String url);

    /** Creates a request that uses search to query for multiple {@link Product products}. */
    SearchRequest<Product> createSearchRequest(String url, ApiMode apiMode, Iterable<FilterExpression> filters);

    // no product modifications yet
    // createCommandRequest
}
