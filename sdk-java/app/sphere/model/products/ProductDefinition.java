package sphere.model.products;

import sphere.model.QueryResult;
import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;

/** Definition of a Product (e.g. 'Shoe'). Any Product must conform to some product definition. */
public class ProductDefinition {
    String id;
    String version;
    String name;
    String description;
    ArrayList<AttributeDefinition> attributes = new ArrayList<AttributeDefinition>();

    // for JSON deserializer
    private ProductDefinition() { }

    public static F.Promise<QueryResult<ProductDefinition>> getAll() throws IOException {
        return WS.url("http://localhost:4242/bias/product-definitions").get().map(
            new ReadJson<QueryResult<ProductDefinition>>(new TypeReference<QueryResult<ProductDefinition>>() { })
        );
    }

    public String getId() { return id; }
    public String getVersion() { return version; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<AttributeDefinition> getAttributes() { return attributes; }
}
