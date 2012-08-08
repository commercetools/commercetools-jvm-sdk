package de.commercetools.sphere.client.model.products;

import java.util.ArrayList;

/** Definition of a product type (e.g. 'Shoe').
 *  Every Product must be based on some product type. */
public class ProductType {
    String id;
    String version;
    String name;
    String description;
    ArrayList<AttributeDefinition> attributes = new ArrayList<AttributeDefinition>();

    // for JSON deserializer
    private ProductType() { }

    public String getId() {
        return id;
    }
    public String getVersion() {
        return version;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public ArrayList<AttributeDefinition> getAttributes() {
        return attributes;
    }
}
