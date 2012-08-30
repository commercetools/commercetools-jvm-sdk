package de.commercetools.sphere.client.shop.model;

/** Custom attribute of a {@link Product}, e.g. 'Color' with a value. */
public class Attribute {
    private String name;
    private Object value;

    // for JSON deserializer
    private Attribute() { }

    /** Name of this attribute, e.g. 'Color'. */
    public String getName() {
        return name;
    }
    /** Value of this attribute, e.g. 'Red'. */
    public Object getValue() {
        return value;
    }

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
