package sphere;

import play.libs.F;
import sphere.model.QueryResult;
import sphere.model.products.Category;

/** Provides access to Sphere backend HTTP APIs for working with Categories. */
public interface Categories {

    /** Queries all categories. */
    F.Promise<QueryResult<Category>> getAll();

    /** Finds a Category by id. */
    F.Promise<Category> getByID(String id);

    /** Finds a Category by a reference. */
    F.Promise<Category> getByReference(String category);
}
