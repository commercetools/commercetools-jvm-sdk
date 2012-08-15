package de.commercetools.sphere.client.shop.model;

/** Custom attribute of a {@link Product}. */
public class Attribute {
    private String name;
    private Object value;

    // for JSON deserializer
    private Attribute() { }

    public String getName() {
        return name;
    }
    public Object getValue() {
        return value;
    }

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
