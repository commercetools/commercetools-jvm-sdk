package sphere;

import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.model.QueryResult;

/** Package private implementation. */
class DefaultCategories extends ProjectScopedAPI implements Categories {

    public DefaultCategories(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    /** Queries all categories. */
    public RequestBuilder<QueryResult<Category>> all() {
        return requestBuilder(endpoints.categories(), new TypeReference<QueryResult<Category>>() {});
    }
}
