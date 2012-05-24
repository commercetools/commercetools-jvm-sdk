package sphere.model.products;

import sphere.model.QueryResult;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;

import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;

/** Category of product in the product catalog. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    String id;
    String version;
    String name;
    String description;
    String parent;

    // for JSON deserializer
    private Category() { }

    /** Queries for all categories. */
    public static F.Promise<QueryResult<Category>> getAll() throws IOException {
        return WS.url("http://localhost:4242/bias/categories").get().map(
                new ReadJson<QueryResult<Category>>(new TypeReference<QueryResult<Category>>() { })
        );
    }

    public String getId() { return id; }
    public String getVersion() { return version; }
    /** Gets the name of this category. */
    public String getName() { return name; }
    /** Gets the description of this category. */
    public String getDescription() { return description; }
    /** Gets a reference to the parent category. */
    public String getParent() { return parent; }
}
