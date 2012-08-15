package sphere;

import play.libs.F;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.products.ProductType;

/** Wraps Sphere HTTP APIs for working with ProductDefinitions in a given project. */
public interface ProductDefinitions {
    /** Queries all Product definitions. */
    RequestBuilder<QueryResult<ProductType>> all();
    /** Queries all Product definitions asynchronously. */
    AsyncRequestBuilder<QueryResult<ProductType>> allAsync();
}
