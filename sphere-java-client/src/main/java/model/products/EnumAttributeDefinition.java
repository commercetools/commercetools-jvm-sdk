package de.commercetools.sphere.client.model.products;

import java.util.Collection;
import java.util.HashSet;

public class EnumAttributeDefinition extends AttributeDefinition {
    HashSet<String> values = new HashSet<String>();

    // for JSON deserializer
    private EnumAttributeDefinition() { }

    public HashSet<String> getValues() {
        return values;
    }
}
