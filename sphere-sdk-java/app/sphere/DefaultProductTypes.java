package sphere;

import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.products.ProductType;

/** Package private implementation. */
class DefaultProductTypes extends ProjectScopedAPI implements ProductTypes {

    public DefaultProductTypes(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    /** Queries all Product definitions. */
    public RequestBuilder<QueryResult<ProductType>> all() {
        return new RequestBuilderImpl<QueryResult<ProductType>>(url(endpoints.productDefinitions()), new TypeReference<QueryResult<ProductType>>() {});
    }
}
