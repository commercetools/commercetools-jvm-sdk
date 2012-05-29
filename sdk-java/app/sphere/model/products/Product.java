package sphere.model.products;

import sphere.model.Money;
import sphere.model.QueryResult;
import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;

import org.codehaus.jackson.type.TypeReference;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Product in the product catalog. */
public class Product {
    String id;
    String version;
    String name;
    String description;
    String sku;
    Money price;
    String definition;
    int quantityAtHand;
    List<String> imageURLs = new ArrayList<String>();
    List<Attribute> attributes = new ArrayList<Attribute>();
    List<String> categories = new ArrayList<String>();
    List<ProductVariant> variants = new ArrayList<ProductVariant>();

    // for JSON deserializer
    private Product() { }

    /** Queries for all products. */
    public static F.Promise<QueryResult<Product>> getAll() {
        return WS.url("http://localhost:4242/bias/products").get().map(
                new ReadJson<QueryResult<Product>>(new TypeReference<QueryResult<Product>>() { })
        );
    }

    /** The main thumbnail image of this product which is the first image in the imageURLs list
     *  Return null if this product has no images. */
    public String getThumbnailImageURL() {
        if (this.imageURLs.isEmpty())
            return null;
        return this.imageURLs.get(0);
    }      

    /** The URL slug of this product. */
    public String getSlug() {
        return getID();
    }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present. */
    public Object getAttribute(String name) {
        for (Attribute a: attributes) {
            if (a.getName().equals(name)) return a.getValue();
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
    /** Price of this product. */
    public Money getPrice() { return price; }
    /** ProductDefinition of this product. */
    public String getDefinition() { return definition; }
    /** SKU (Stock-Keeping-Unit identifier) of this product. */
    public String getSku() { return sku; }
    /** Current available stock quantity for this product. */
    public int getQuantityAtHand() { return quantityAtHand; }
    /** URLs of images attached to this product. */
    public List<String> getImageURLs() { return imageURLs; }
    /** Custom attributes of this product. */
    public List<Attribute> getAttributes() { return attributes; }
    /** Categories this product is assigned to. */
    public List<String> getCategories() { return categories; }
    /** Variants of this product. */
    public List<ProductVariant> getVariants() { return variants; }
}
