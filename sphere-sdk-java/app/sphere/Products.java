package sphere;

import play.libs.F;
import sphere.model.QueryResult;
import sphere.model.products.Product;

/** Provides access to Sphere backend HTTP APIs for working with Products. */
public interface Products {

    /** Queries all products. */
    F.Promise<QueryResult<Product>> getAll();

    /** Queries all products in a given category. */
    F.Promise<QueryResult<Product>> getByCategory(final String category);

    /** Finds a product by id. */
    F.Promise<Product> getByID(String id);
}
