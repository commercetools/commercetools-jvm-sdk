package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.SearchQueryResult;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.SearchRequestBuilder;

/** Sphere HTTP APIs for working with Products in a given project. */
public interface Products {
    /** Creates a request builder that finds a product by id. */
    RequestBuilder<Product> byId(String id);

    /** Creates a request builder that queries all products. */
    RequestBuilder<QueryResult<Product>> all();

    /** Creates a request builder that searches products.
     *  @param fulltextQuery Fulltext search query that matches any searchable product attributes. */
    SearchRequestBuilder<Product> search(String fullTextQuery);

    /** Creates a request builder that searches products. */
    SearchRequestBuilder<Product> search();
}
