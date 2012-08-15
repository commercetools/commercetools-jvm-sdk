package sphere;

import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.model.QueryResult;
import play.libs.F;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;

/** Package private implementation. */
class DefaultProducts extends ProjectScopedAPI implements Products {

    public DefaultProducts(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    /** Finds a product by id. */
    public RequestBuilder<Product> id(String id) {
        return new SyncRequestBuilderImpl<Product>(idAsync(id));
    }
    /** Finds a product by id asynchronously. */
    public AsyncRequestBuilder<Product> idAsync(String id) {
        return new AsyncRequestBuilderImpl<Product>(
                url(endpoints.product(id)), new TypeReference<Product>() {});
    }

    /** Queries all products. */
    public RequestBuilder<QueryResult<Product>> all() {
        return new SyncRequestBuilderImpl<QueryResult<Product>>(allAsync());
    }
    /** Queries all products asynchronously. */
    public AsyncRequestBuilder<QueryResult<Product>> allAsync() {
        return new AsyncRequestBuilderImpl<QueryResult<Product>>(
                url(endpoints.products()), new TypeReference<QueryResult<Product>>() {});
    }
}
