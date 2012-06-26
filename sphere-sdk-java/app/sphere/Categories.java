package sphere;

import play.libs.F;
import sphere.model.QueryResult;
import sphere.model.products.Category;

/** Wraps Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {

    /** Queries all categories. */
    F.Promise<QueryResult<Category>> getAll();

    /** Finds a Category by id. */
    F.Promise<Category> getByID(String id);

    /** Finds a Category by a reference. */
    F.Promise<Category> getByReference(String category);
}
