package sphere;

import sphere.model.QueryResult;
import play.libs.F;
import sphere.util.ReadJson;
import sphere.model.products.Category;

import org.codehaus.jackson.type.TypeReference;

import java.util.List;

/** Package private implementation. */
class DefaultCategories extends ProjectScopedAPI implements Categories {

    public DefaultCategories(String project, ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(project, credentials, endpoints);
    }

    /** Queries all categories. */
    public F.Promise<QueryResult<Category>> getAll() {
        return url(endpoints.categories()).get().map(
            new ReadJson<QueryResult<Category>>(new TypeReference<QueryResult<Category>>() {})
        );
    }

    /** Finds a Category by id. */
    public F.Promise<Category> getByID(final String id) {
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

    /** Finds a Category by a reference. */
    public F.Promise<Category> getByReference(String category) {
        if (category == null) throw new IllegalArgumentException("category");
        return getByID(category.split(":")[1]);
    }

    private F.Promise<QueryResult<Category>> getSubtree(String rootID) {
        return getAll();
    }
    private Category buildChildren(Category root, List<Category> all) {
        String rootRef = root.getReference();
        for(Category c: all) {
            if (c.getParent() != null && c.getParent().equals(rootRef)) {
                root.getChildren().add(c);
                buildChildren(c, all);
            }
        }
        return root;
    }
}
