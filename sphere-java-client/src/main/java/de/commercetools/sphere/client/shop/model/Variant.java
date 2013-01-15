package de.commercetools.sphere.client.shop.model;

import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.model.Money;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /** Returns the value of custom attribute with given name.
     *  @return Returns null if no such attribute is present. */
    public Object get(String name) {
        for (Attribute a: attributes) {
            if (a.getName().equals(name)) {
                return a.getValue();
            }
        }
        return null;
    }

    /** Returns the value of a custom string attribute.
     *  @return Returns an empty string if no such attribute is present or if it is not a string. */
    public String getString(String attributeName) {
        Object v = get(attributeName);
        if (v == null || !(v instanceof String)) return "";
        return (String)v;
    }

    /** Returns the value of a custom number attribute.
     *  @return Returns 0 if no such attribute is present or if it is not an int. */
    public int getInt(String attributeName) {
        Object v = get(attributeName);
        if (v == null || !(v instanceof Integer)) return 0;
        return (Integer)v;
    }

    /** Returns the value of a custom number attribute.
     *  @return Returns 0 if no such attribute is present or if it is not a double. */
    public double getDouble(String attributeName) {
        Object v = get(attributeName);
        if (v == null || !(v instanceof Double)) return 0.0;
        return (Double)v;
    }

    /** Returns the value of a custom money attribute.
     *  @return Returns null if no such attribute is present or if it is not of type Money. */
    public Money getMoney(String attributeName) {
        // Jackson has no way of knowing that an attribute is a money attribute and its value should be parsed as Money.
        // It sees a json object {'currencyCode':'EUR','centAmount':1200} and parses it as LinkedHashMap.
        Object o = get(attributeName);
        if (o == null || (!(o instanceof Map))) return null;
        return new ObjectMapper().convertValue(o, Money.class);
    }

    private static DateTimeFormatter dateFormat = ISODateTimeFormat.localDateParser();
    /** Returns the value of a custom date attribute.
     *  @return Returns null if no such attribute is present or if it is not a LocalDate. */
    public LocalDate getDate(String attributeName) {
        String s = getString(attributeName);
        if (s == null) return null;
        return dateFormat.parseLocalDate(s);
    }

    private static DateTimeFormatter timeFormat = ISODateTimeFormat.localTimeParser();
    /** Returns the value of a custom time attribute.
     *  @return Returns null if no such attribute is present or if it is not a LocalTime. */
    public LocalTime getTime(String attributeName) {
        String s = getString(attributeName);
        if (s == null) return null;
        return timeFormat.parseLocalTime(s);
    }

    private static DateTimeFormatter dateTimeFormat = ISODateTimeFormat.dateTimeParser();
    /** Returns the value of a custom DateTime attribute.
     *  @return Returns null if no such attribute is present or if it is not a DateTime. */
    public DateTime getDateTime(String attributeName) {
        String s = getString(attributeName);
        if (s == null) return null;
        return dateTimeFormat.parseDateTime(s);
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
