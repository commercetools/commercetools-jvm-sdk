package de.commercetools.sphere.client.model.products;

public class DateAttributeDefinition extends AttributeDefinition {

    public DateAttributeDefinition(String name, boolean isRequired, boolean isVariant) {
        super(name, isRequired, isVariant);
    }

    // for JSON deserializer
    private DateAttributeDefinition() { }
}
