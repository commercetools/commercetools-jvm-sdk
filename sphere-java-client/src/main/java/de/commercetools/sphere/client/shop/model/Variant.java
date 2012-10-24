package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Money;

import java.util.ArrayList;
import java.util.List;

/** Variant of a product in the product catalog. */
public class Variant {
    private String id;
    private String sku;
    private Money price;
    private List<String> imageURLs = new ArrayList<String>();
    private List<Attribute> attributes = new ArrayList<Attribute>();

    // for JSON deserializer
    protected Variant() { }

    /** The main image for this variant which is the first image in the {@link #getImageURLs()} list.
     *  Return null if this variant has no images. */
    public String getFirstImageURL() {
        if (this.imageURLs.isEmpty())
            return null;
        return this.imageURLs.get(0);
    }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present.
     *  Casts the value to given type. Throws {@link ClassCastException} if the actual type of value is different. */
    @SuppressWarnings("unchecked")
    public <T> T getAttributeAs(String name) {
        return (T)getAttribute(name);
    }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present. */
    public Object getAttribute(String name) {
        for (Attribute a: attributes) {
            if (a.getName().equals(name)) {
                return a.getValue();
            }
        }
        return null;
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this variant. An id is never empty. */
    public String getId() { return id; }

    /** SKU (Stock Keeping Unit) of this variant. SKUs are optional. */
    public String getSKU() { return sku; }

    /** Price of this variant. */
    public Money getPrice() { return price; }

    /** URLs of images attached to this variant. */
    public List<String> getImageURLs() { return imageURLs; }

    /** Custom attributes of this variant. */
    public List<Attribute> getAttributes() { return attributes; }
}
