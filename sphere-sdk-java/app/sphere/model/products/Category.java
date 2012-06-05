package sphere.model.products;

import sphere.Config;
import sphere.model.QueryResult;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import play.libs.F;

import java.util.ArrayList;
import java.util.List;

/** Category of product in the product catalog. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    private String id;
    private String version;
    private String name;
    private String description;
    private String parent;
    private List<Category> children = new ArrayList<Category>();
    
    public String getReference() {
        return "category:" + this.getID();
    }

    /** Queries all categories. */
    public static F.Promise<QueryResult<Category>> getAll() {
        return sphere.extra.Categories.getAll(Config.projectName());
    }

    /** Finds a Category by id. */
    public static F.Promise<Category> getByID(String id) {
        return sphere.extra.Categories.getByID(Config.projectName(), id);
    }

    /** Finds a Category by a reference. */
    public static F.Promise<Category> getByReference(String category) {
        return sphere.extra.Categories.getByReference(Config.projectName(), category);
    }

    // for JSON deserializer
    private Category() { }

    /** The URL slug of this product. */
    public String getSlug() {
        return sphere.Ext.slugify(getName());
    }

    public String getID() { return id; }
    public String getVersion() { return version; }
    /** Gets the name of this category. */
    public String getName() { return name; }
    /** Gets the description of this category. */
    public String getDescription() { return description; }
    /** Gets a reference to the parent category. */
    public String getParent() { return parent; }
    /** Gets child categories of this category. */
    public List<Category> getChildren() { return children; }
}
