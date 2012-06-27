package sphere;

import play.libs.F;
import sphere.model.QueryResult;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;
import sphere.model.products.ProductDefinition;

/** Package private implementation. */
public class DefaultProductDefinitions extends ProjectScopedAPI implements ProductDefinitions {

    public DefaultProductDefinitions(String project, ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(project, credentials, endpoints);
    }

    public F.Promise<QueryResult<ProductDefinition>> getAll() {
        return url(endpoints.productDefinitions()).get().map(
            new ReadJson<QueryResult<ProductDefinition>>(new TypeReference<QueryResult<ProductDefinition>>() {})
        );
    }
}
