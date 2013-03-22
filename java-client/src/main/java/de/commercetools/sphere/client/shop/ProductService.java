package io.sphere.client.shop;

import io.sphere.client.FetchRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.SearchRequest;
import io.sphere.client.shop.model.Product;

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
    SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters);

    /** Queries products based on given constraints.
     *  @param filters Filters describing query for products. */
    SearchRequest<Product> filter(Iterable<FilterExpression> filters);
}
