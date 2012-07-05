package de.commercetools.sphere.client.model.products;

public class TimeAttributeDefinition extends AttributeDefinition {
    public TimeAttributeDefinition(String name, boolean isRequired, boolean isVariant) {
        super(name, isRequired, isVariant);
    }

    // for the JSON deserializer
    private TimeAttributeDefinition() { }
}
