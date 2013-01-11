package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.filters.expressions.FilterExpression;
import de.commercetools.sphere.client.SearchRequest;
import de.commercetools.sphere.client.shop.model.Product;

/** Sphere HTTP APIs for working with Products in a given project. */
public interface ProductService {
    /** Creates a request that finds a product by id. */
    FetchRequest<Product> byId(String id);

    /** Creates a request that finds a product by slug. */
    FetchRequest<Product> bySlug(String slug);

    /** Creates a request that returns all products. */
    SearchRequest<Product> all();

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequest<Product> filtered(FilterExpression filter, FilterExpression... filters);

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequest<Product> filtered(Iterable<FilterExpression> filters);
}
