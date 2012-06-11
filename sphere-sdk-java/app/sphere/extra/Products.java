package sphere.extra;

import sphere.model.QueryResult;
import play.libs.F;
import play.libs.WS;
import sphere.util.Endpoints;
import sphere.util.ReadJson;
import sphere.model.products.Product;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;

/** Provides access to Sphere APIs for working with Products. */
public class Products {
    /** Queries all products. */
    public static F.Promise<QueryResult<Product>> getAll(String project) {
        return WS.url(Endpoints.project(project).products()).get().map(
            new ReadJson<QueryResult<Product>>(new TypeReference<QueryResult<Product>>() {})
        );
    }

    /** Queries all products in a given category. */
    public static F.Promise<QueryResult<Product>> getByCategory(String project, final String category) {
        if (category == null || category.equals("")) {
            return getAll(project);
        }
        // until we have query APIs on the backend
        return getAll(project).map(new F.Function<QueryResult<Product>, QueryResult<Product>>() {
            @Override
            public QueryResult<Product> apply(QueryResult<Product> qr) throws Throwable {
                ArrayList<Product> res = new ArrayList<Product>();
                for(Product p: qr.getResults()) {
                    if (p.getCategories().contains(category)) {
                        res.add(p);
                    }
                }
                return new QueryResult<Product>(0, res.size(), res.size(), res);
            }
        });
    }

    /** Finds a product by id. */
    public static F.Promise<Product> getByID(String project, String id) {
        return WS.url(Endpoints.project(project).product(id)).get().map(
            new ReadJson<Product>(new TypeReference<Product>() {})
        );
    }
}
