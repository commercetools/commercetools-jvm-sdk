package sphere.model.products;

import sphere.Config;
import sphere.Log;
import sphere.model.QueryResult;
import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 *  Product in the product catalog.
 *
 *  The Product itself is a {@link Variant}. Products that only exist in one variant
 *  are represented by a single Product instance where {@link #getVariants()} is empty.
 *  */
public class Product extends Variant {
    private String id;
    private String version;
    private String name;
    private String description;
    private String definition;
    private int quantityAtHand;
    private List<String> categories = new ArrayList<String>();
    private List<Variant> variants = new ArrayList<Variant>();

    // for JSON deserializer
    private Product() { }
    
    /** Queries all products. */
    public static F.Promise<QueryResult<Product>> getAll() {
        return WS.url(Config.projectEndpoint + "/products").get().map(
                new ReadJson<QueryResult<Product>>(new TypeReference<QueryResult<Product>>() { })
        );
    }

    /** Queries all products in a given category. */
    public static F.Promise<QueryResult<Product>> getByCategory(final String category) {
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
    public static F.Promise<Product> getByID(String id) {
        return WS.url(Config.projectEndpoint + "/products/" + id).get().map(
                new ReadJson<Product>(new TypeReference<Product>() { })
        );
    }

    /** The URL slug of this product. */
    public String getSlug() {
        return sphere.Ext.slugify(getName());
    }

    /** Returns the variant with given SKU, or null if such variant does not exist. */
    public Variant getVariantBySKU(String sku) {
        for (Variant v: variants) {
            if (v.getSKU().equals(sku)) return v;
        }
        return null;
    }

    /** Id of this product. */
    public String getID() { return id; }
    /** Version (modification revision) of this product. */
    public String getVersion() { return version; }
    /** Name of this product. */
    public String getName() { return name; }
    /** Description of this product. */
    public String getDescription() { return description; }
    /** ProductDefinition of this product. */
    public String getDefinition() { return definition; }
    /** Current available stock quantity for this product. */
    public int getQuantityAtHand() { return quantityAtHand; }
    /** Categories this product is assigned to. */
    public List<String> getCategories() { return categories; }
    /** Variants of this product. */
    public List<Variant> getVariants() { return variants; }
}
