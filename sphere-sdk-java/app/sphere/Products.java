package sphere;

import play.libs.F;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Product;

/** Wraps Sphere HTTP APIs for working with Products in a given project. */
public interface Products {
    /** Finds a product by id. */
    RequestBuilder<Product> id(String id);
    /** Finds a product by id asynchronously. */
    AsyncRequestBuilder<Product> idAsync(String id);

    /** Queries all products. */
    RequestBuilder<QueryResult<Product>> all();
    /** Queries all products asynchronously. */
    AsyncRequestBuilder<QueryResult<Product>> allAsync();
}
