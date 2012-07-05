package sphere;

import de.commercetools.sphere.client.model.QueryResult;
import play.libs.F;
import sphere.util.ReadJson;
import de.commercetools.sphere.client.model.products.Product;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;

/** Package private implementation. */
class DefaultProducts extends ProjectScopedAPI implements Products {
    
    public DefaultProducts(ClientCredentials credentials, ProjectEndpoints endpoints) {
        super(credentials, endpoints);
    }

    /** Queries all products. */
    public F.Promise<QueryResult<Product>> getAll() {
        return url(endpoints.products()).get().map(
            new ReadJson<QueryResult<Product>>(new TypeReference<QueryResult<Product>>() {})
        );
    }

    /** Queries all products in a given category. */
    public F.Promise<QueryResult<Product>> getByCategory(final String category) {
        if (category == null || category.equals("")) {
            return getAll();
        }
        // until we have query APIs on the backend
        return getAll().map(new F.Function<QueryResult<Product>, QueryResult<Product>>() {
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
    public F.Promise<Product> getByID(String id) {
        return url(endpoints.product(id)).get().map(
            new ReadJson<Product>(new TypeReference<Product>() {})
        );
    }
}
