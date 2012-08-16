package sphere;

import play.libs.F;
import org.codehaus.jackson.type.TypeReference;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.model.QueryResult;

import java.util.List;

/** Package private implementation. */
class DefaultCategories extends ProjectScopedAPI implements Categories {

    public DefaultCategories(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    /** Queries all categories. */
    public RequestBuilder<QueryResult<Category>> all() {
        return new RequestBuilderImpl<QueryResult<Category>>(url(endpoints.categories()), new TypeReference<QueryResult<Category>>() {});
    }
}
