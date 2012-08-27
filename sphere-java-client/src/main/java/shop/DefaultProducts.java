package de.commercetools.sphere.client.shop;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.model.Product;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import org.codehaus.jackson.type.TypeReference;

/** Package private implementation. */
public class DefaultProducts extends ProjectScopedAPI implements Products {

    public DefaultProducts(AsyncHttpClient httpClient, ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(httpClient, credentials, endpoints);
    }

    /** Finds a product by id. */
    public RequestBuilder<Product> byId(String id) {
        return requestBuilder(endpoints.product(id), new TypeReference<Product>() {});
    }

    /** Queries all products. */
    public RequestBuilder<QueryResult<Product>> all() {
        return requestBuilder(endpoints.products(), new TypeReference<QueryResult<Product>>() {});
    }
}
