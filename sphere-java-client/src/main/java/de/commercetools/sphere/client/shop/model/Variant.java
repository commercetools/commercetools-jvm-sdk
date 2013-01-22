package de.commercetools.sphere.client.shop.model;

import de.commercetools.sphere.client.model.Money;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/** Variant of a product in a product catalog. */
public class Variant {
    private int id;
    private String sku;
    private Money price;
    private List<String> imageURLs = new ArrayList<String>();
    private List<Attribute> attributes = new ArrayList<Attribute>();
    private VariantAvailability availability;

    // for JSON deserializer
    protected Variant() { }

    // used by tests
    Variant(int id, String sku, Money price, List<String> imageURLs, List<Attribute> attributes) {
        this.id = id;
        this.sku = sku;
        this.price = price;
        this.imageURLs = imageURLs;
        this.attributes = attributes;
    }

    /** The main image for this variant which is the first image in the {@link #getImageURLs()} list.
     *  Return null if this variant has no images. */
    public String getFirstImageURL() {
        if (this.imageURLs.isEmpty())
            return null;
        return this.imageURLs.get(0);
    }

    // --------------------------------------------------------
    // Get attribute
    // --------------------------------------------------------

    /** Returns true if a custom attribute with given name is present. */
    public boolean hasAttribute(String attributeName) {
        return getAttribute(attributeName) != null;
    }

    /** Finds custom attribute with given name. Returns null if no such attribute exists. */
    public Attribute getAttribute(String attributeName) {
        for (Attribute a: attributes) {
            if (a.getName().equals(attributeName)) {
                return a;
            }
        }
        return null;
    }

    /** Returns the value of custom attribute.
     *  @return The value or null if no such attribute is present. */
    public Object get(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? null : a.getValue();
    }

    /** Returns the value of a custom string attribute.
     *  @return The value or empty string if no such attribute is present or its value is not a string. */
    public String getString(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultString : a.getString();
    }

    /** Returns the value of a custom number attribute.
     *  @return The value or 0 if no such attribute is present or its value is not an int. */
    public int getInt(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultInt : a.getInt();
    }

    /** Returns the value of a custom number attribute.
     *  @return The value or 0.0 if no such attribute is present or its value is not a double. */
    public double getDouble(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultDouble : a.getDouble();
    }

    /** Returns the value of a custom money attribute.
     *  @return The value or null if no such attribute is present or its value of not a Money instance. */
    public Money getMoney(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultMoney : a.getMoney();
    }

    /** Returns the value of a custom DateTime attribute.
     *  @return The value or null if no such attribute is present or its value is not a DateTime. */
    public DateTime getDateTime(String attributeName) {
        Attribute a = getAttribute(attributeName);
        return a == null ? Attribute.defaultDateTime : a.getDateTime();
    }

    // --------------------------------------------------------
    // Getters
    // --------------------------------------------------------

    /** Unique id of this variant. An id is never empty. */
    public int getId() { return id; }

    /** SKU (Stock Keeping Unit) of this variant. SKUs are optional. */
    public String getSKU() { return sku; }

    /** Price of this variant. */
    public Money getPrice() { return price; }

    /** URLs of images attached to this variant. */
    public List<String> getImageURLs() { return imageURLs; }

    /** Custom attributes of this variant. */
    public List<Attribute> getAttributes() { return attributes; }
}
