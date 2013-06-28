package io.sphere.client.shop;

import io.sphere.client.FetchRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.SearchRequest;
import io.sphere.client.shop.model.Product;

import java.util.Locale;

/** Sphere HTTP API for working with products in a given project. */
public interface ProductService {
    /** Finds a product by id. */
    FetchRequest<Product> byId(String id);

    /** Finds a product by slug. */
    FetchRequest<Product> bySlug(String slug, Locale locale);

    /** Fetches all products. */
    SearchRequest<Product> all(Locale locale);

    /** Finds products satisfying given constraints.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(Locale locale, FilterExpression filter, FilterExpression... filters);

    /** Finds products satisfying given constraints.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(Locale locale, Iterable<FilterExpression> filters);
}
