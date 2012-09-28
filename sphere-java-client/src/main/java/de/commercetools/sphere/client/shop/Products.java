package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.Filter;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.SearchRequestBuilder;

import java.util.Collection;

/** Sphere HTTP APIs for working with Products in a given project. */
public interface Products {
    /** Creates a request builder that finds a product by id. */
    RequestBuilder<Product> byId(String id);

    /** Creates a request builder that queries all products. */
    SearchRequestBuilder<Product> all();

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequestBuilder<Product> filter(Filter... filters);

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequestBuilder<Product> filter(Collection<Filter> filters);
}
