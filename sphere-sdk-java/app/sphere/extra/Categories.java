package sphere.extra;

import sphere.Log;
import sphere.model.QueryResult;
import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;
import sphere.model.products.Category;

import org.codehaus.jackson.type.TypeReference;

import java.util.List;

/** Provides access to Sphere APIs for working with Categories. */
public class Categories {

    /** Queries all categories. */
    public static F.Promise<QueryResult<Category>> getAll(String project) {
        return WS.url(Routes.project(project).categories()).get().map(
                new ReadJson<QueryResult<Category>>(new TypeReference<QueryResult<Category>>() { })
        );
    }

    /** Finds a Category by id. */
    public static F.Promise<Category> getByID(String project, final String id) {
        return getSubtree(project, id).map(new F.Function<QueryResult<Category>, Category>() {
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

    private static F.Promise<QueryResult<Category>> getSubtree(String project, String rootID) {
        return getAll(project);
    }
    private static Category buildChildren(Category root, List<Category> all) {
        String rootRef = root.getReference();
        for(Category c: all) {
            if (c.getParent() != null && c.getParent().equals(rootRef)) {
                root.getChildren().add(c);
                buildChildren(c, all);
            }
        }
        return root;
    }

    /** Finds a Category by a reference. */
    public static F.Promise<Category> getByReference(String project, String category) {
        if (category == null) throw new IllegalArgumentException("category");
        return getByID(project, category.split(":")[1]);
    }
}
