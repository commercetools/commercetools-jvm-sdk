package sphere.extra;

import play.libs.F;
import play.libs.WS;
import sphere.model.QueryResult;
import sphere.util.Endpoints;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;
import sphere.model.products.ProductDefinition;

/** Sphere HTTP APIs for ProductDefinitions in a given project. */
public class ProductDefinitions implements sphere.ProductDefinitions {
    
    private String project;

    public ProductDefinitions(String project) {
        this.project = project;
    }

    public F.Promise<QueryResult<ProductDefinition>> getAll() {
        return WS.url(Endpoints.project(project).productDefinitions()).get().map(
            new ReadJson<QueryResult<ProductDefinition>>(new TypeReference<QueryResult<ProductDefinition>>() {})
        );
    }
}
