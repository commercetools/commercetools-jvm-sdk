package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Reference;
import de.commercetools.sphere.client.model.Money;

import java.util.ArrayList;
import java.util.List;

/** Variant of a product in the product catalog. */
public class Variant {

    protected String sku;
    protected Money price;
    protected List<String> imageURLs = new ArrayList<String>();
    protected List<Attribute> attributes = new ArrayList<Attribute>();

    // for JSON deserializer
    protected Variant() { }

    /** The main thumbnail image of this product which is the first image in the imageURLs list
     *  Return null if this product has no images. */
    public String getThumbnailImageURL() {
        if (this.imageURLs.isEmpty())
            return null;
        return this.imageURLs.get(0);
    }

    /** Returns the value of custom attribute with given name, or null if the attribute is not present. */
    public Object getAttribute(String name) {
        for (Attribute a: attributes) {
            if (a.getName().equals(name)) return a.getValue();
        }
        return null;
    }

    /** SKU (Stock-Keeping-Unit identifier) of this variant. */
    public String getSKU() {
        return sku;
    }
    /** Price of this variant. */
    public Money getPrice() {
        return price;
    }
    /** URLs of images attached to this variant. */
    public List<String> getImageURLs() {
        return imageURLs;
    }
    /** Custom attributes of this variant. */
    public List<Attribute> getAttributes() {
        return attributes;
    }
}
