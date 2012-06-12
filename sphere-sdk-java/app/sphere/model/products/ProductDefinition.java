package sphere.model.products;

import sphere.Config;
import sphere.model.QueryResult;
import java.util.ArrayList;
import play.libs.F;

/** Definition of a Product (e.g. 'Shoe').
 *  Every Product must be based on some product definition. */
public class ProductDefinition {
    String id;
    String version;
    String name;
    String description;
    ArrayList<AttributeDefinition> attributes = new ArrayList<AttributeDefinition>();

    // for JSON deserializer
    private ProductDefinition() { }

    public String getId() { return id; }
    public String getVersion() { return version; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<AttributeDefinition> getAttributes() { return attributes; }
}
