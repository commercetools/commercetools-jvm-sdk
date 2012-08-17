package sphere;

import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Category;

/** Wraps Sphere HTTP APIs for working with Categories in a given project. */
public interface Categories {
    /** Queries all categories. */
    RequestBuilder<QueryResult<Category>> all();
}
