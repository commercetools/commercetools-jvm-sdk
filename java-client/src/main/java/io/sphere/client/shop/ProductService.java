package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.SearchRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Product;
import io.sphere.client.shop.model.ProductUpdate;

import java.util.Locale;

/** Sphere HTTP API for working with products in a given project. */
public interface ProductService {
    /** Finds a product by id. */
    FetchRequest<Product> byId(String id);

    /** Finds a product by slug. */
    FetchRequest<Product> bySlug(Locale locale, String slug);

    /** Finds a product by slug using the Sphere clients default locale. */
    FetchRequest<Product> bySlug(String slug);

    /** Fetches all products. */
    SearchRequest<Product> all(Locale locale);

    /** Fetches all products using the default locale of the Sphere client */
    SearchRequest<Product> all();

    /** Finds products satisfying given constraints.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(Locale locale, FilterExpression filter, FilterExpression... filters);

    /** Finds products satisfying given constraints using the Sphere clients default locale.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters);

    /** Finds products satisfying given constraints.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(Locale locale, Iterable<FilterExpression> filters);

    /** Queries for products. */
    QueryRequest<Product> query();

    /** Updates a product. */
    CommandRequest<Product> updateProduct(VersionedId productId, ProductUpdate update);
}
