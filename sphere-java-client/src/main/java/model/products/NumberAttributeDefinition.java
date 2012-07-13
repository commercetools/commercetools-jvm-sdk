package de.commercetools.sphere.client.model.products;

public class NumberAttributeDefinition extends AttributeDefinition {

    public NumberAttributeDefinition(String name, boolean isRequired, boolean isVariant) {
        super(name, isRequired, isVariant);
    }

    // for JSON deserializer
    private NumberAttributeDefinition() { }
}
