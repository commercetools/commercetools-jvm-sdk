package sphere.model.products;

import java.util.ArrayList;
import java.util.List;

import sphere.model.Money;

public class ProductVariant {
    String sku;
    Money price;
    List<String> imageURLs = new ArrayList<String>();
    List<Attribute> attributes = new ArrayList<Attribute>();

    // for JSON deserializer
    private ProductVariant() { }

    /** SKU (Stock-Keeping-Unit identifier) of this variant. */
    public String getSku() { return sku; }
    /** Price of this variant. */
    public Money getPrice() { return price; }
    /** URLs of images attached to this variant. */
    public List<String> getImageURLs() { return imageURLs; }
    /** Custom attributes of this variant. */
    public List<Attribute> getAttributes() { return attributes; }
}