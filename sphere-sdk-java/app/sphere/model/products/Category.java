package sphere.model.products;

import sphere.Config;
import sphere.Log;
import sphere.model.QueryResult;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;

import org.codehaus.jackson.type.TypeReference;

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

    // for JSON deserializer
    private Category() { }

    /** Queries all categories. */
    public static F.Promise<QueryResult<Category>> getAll() {
        return WS.url(Config.projectEndpoint + "/categories").get().map(
                new ReadJson<QueryResult<Category>>(new TypeReference<QueryResult<Category>>() { })
        );
    }

    /** Finds a Category by id. */
    public static F.Promise<Category> getByID(final String id) {
        return getSubtree(id).map(new F.Function<QueryResult<Category>, Category>() {
            @Override
            public Category apply(QueryResult<Category> qr) throws Throwable {
                try {
                    List<Category> all = qr.getResults();
                    Category category = null;
                    for(Category c: all) {
                        if (c.getID().equals(id)) {
                            category = c;
                            break;
                        }
                    }
                    return buildChildren(category, all);
                } catch (Exception e) {
                    // Workaround for exception swallowing bug in Play 2.0 - at least print the stack trace
                    // (fixed in master, so this can be removed with the next release of Play)
                    Log.error(e);
                    throw e;
                }
            }
        });
    }

    private static F.Promise<QueryResult<Category>> getSubtree(String id) {
        return getAll();
    } 
    private static Category buildChildren(Category root, List<Category> all) {
        String rootRef = root.getReference();
        for(Category c: all) {
            if (c.getParent() != null && c.getParent().equals(rootRef)) {
                root.children.add(c);
                buildChildren(c, all);
            }
        }
        return root;
    }

    /** The URL slug of this product. */
    public String getSlug() {
        return sphere.Ext.slugify(getName());
    }

    /** Finds a Category by a reference. */
    public static F.Promise<Category> getByReference(String category) {
        if (category == null) throw new IllegalArgumentException("category");
        return getByID(category.split(":")[1]);
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
