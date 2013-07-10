package sphere;

import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.model.Product;

import java.util.Locale;

/** Sphere HTTP API for working with products in a given project. */

// You may think that this service is an exact copy of io.sphere.client.shop.ProductService but its FetchRequests
// use Play's Futures rather than Guava's
public interface ProductService {
    /** Finds a product by id. */
    FetchRequest<Product> byId(String id);

    /** Finds a product by slug. */
    FetchRequest<Product> bySlug(Locale locale, String slug);

    /** Finds a product by slug using the Sphere clients default locale. */
    FetchRequest<Product> bySlug(String slug);

    /**
     * Creates a SearchRequest configured with a given locale for all products.
     * @param locale Influences various aspects of full-text searching, filtering, sorting and faceting but not the
     *               translations returned in the result. The Sphere API will always return all translations of
     *               localized attributes.
     */
    SearchRequest<Product> all(Locale locale);

    /**
     * Creates a SearchRequest configured with the default locale of this SphereClient instance.
     * @see #all(java.util.Locale)
     */
    SearchRequest<Product> all();

    /** Finds products satisfying given constraints.
     *  @param locale  Influences filtering operations, but not the translations returned.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND).
     */
    SearchRequest<Product> filter(Locale locale, FilterExpression filter, FilterExpression... filters);

    /**
     * @return A filtering request that applies filters as well as the configured default locale.
     * @see #filter(java.util.Locale, io.sphere.client.filters.expressions.FilterExpression, io.sphere.client.filters.expressions.FilterExpression...)
     */
    SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters);

    /** Finds products satisfying given constraints.
     *  @param locale  Influences filtering operations, but not the translations returned.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(Locale locale, Iterable<FilterExpression> filters);


    /**
     * @return A filtering request that applies filters as well as the configured default locale.
     * @see #filter(java.util.Locale, Iterable)
     */
    SearchRequest<Product> filter(Iterable<FilterExpression> filters);
}
