package sphere.extra;

import play.libs.F;
import sphere.Endpoints;
import sphere.ProjectEndpoints;
import sphere.model.QueryResult;
import sphere.util.OAuthCredentials;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;
import sphere.model.products.ProductDefinition;

/** Sphere HTTP APIs for ProductDefinitions in a given project. */
public class ProductDefinitions extends ProjectAPI implements sphere.ProductDefinitions {

    public ProductDefinitions(String project, OAuthCredentials credentials) {
        super(project, credentials);
    }

    public F.Promise<QueryResult<ProductDefinition>> getAll() {
        return url(endpoints.productDefinitions()).get().map(
            new ReadJson<QueryResult<ProductDefinition>>(new TypeReference<QueryResult<ProductDefinition>>() {})
        );
    }
}
