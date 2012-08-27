package de.commercetools.sphere.client.shop;

import com.ning.http.client.AsyncHttpClient;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import org.codehaus.jackson.type.TypeReference;

/** Package private implementation. */
public class DefaultCategories extends ProjectScopedAPI implements Categories {

    public DefaultCategories(AsyncHttpClient httpClient, ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(httpClient, credentials, endpoints);
    }

    /** Queries all categories. */
    public RequestBuilder<QueryResult<Category>> all() {
        return requestBuilder(endpoints.categories(), new TypeReference<QueryResult<Category>>() {});
    }
}
