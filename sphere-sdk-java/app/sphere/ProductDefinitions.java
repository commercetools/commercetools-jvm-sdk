package sphere;

import play.libs.F;
import sphere.model.QueryResult;
import sphere.model.products.ProductDefinition;

/** Provides access to Sphere backend HTTP APIs for working with Product definitions. */
public interface ProductDefinitions {

    /** Queries all Product definitions. */
    F.Promise<QueryResult<ProductDefinition>> getAll();
}
