package sphere;

import io.sphere.client.filters.expressions.FilterExpression;
import io.sphere.client.shop.model.Product;

/** Sphere HTTP API for working with products in a given project. */
public interface ProductService {
    /** Finds a product by id. */
    FetchRequest<Product> byId(String id);

    /** Finds a product by slug. */
    FetchRequest<Product> bySlug(String slug);

    /** Fetches all products. */
    SearchRequest<Product> all();

    /** Finds products satisfying given constraints.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(FilterExpression filter, FilterExpression... filters);

    /** Finds products satisfying given constraints.
     *  @param filters Filters describing a query. The filters are used in conjunction (AND). */
    SearchRequest<Product> filter(Iterable<FilterExpression> filters);
}
