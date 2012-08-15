package sphere;

import play.libs.F;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.model.products.ProductType;

/** Package private implementation. */
class DefaultProductDefinitions extends ProjectScopedAPI implements ProductDefinitions {

    public DefaultProductDefinitions(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    /** Queries all Product definitions. */
    public RequestBuilder<QueryResult<ProductType>> all() {
        return new SyncRequestBuilderImpl<QueryResult<ProductType>>(allAsync());
    }
    /** Queries all Product definitions asynchronously. */
    public AsyncRequestBuilder<QueryResult<ProductType>> allAsync() {
        return new AsyncRequestBuilderImpl<QueryResult<ProductType>>(
                url(endpoints.productDefinitions()), new TypeReference<QueryResult<ProductType>>() {});
    }
}
