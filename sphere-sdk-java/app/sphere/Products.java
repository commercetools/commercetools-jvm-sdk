package sphere;

import play.libs.F;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Product;

/** Wraps Sphere HTTP APIs for working with Products in a given project. */
public interface Products {
    /** Finds a product by id. */
    RequestBuilder<Product> byId(String id);

    /** Queries all products. */
    RequestBuilder<QueryResult<Product>> all();
}
