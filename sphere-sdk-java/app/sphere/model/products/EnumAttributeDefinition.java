package sphere.model.products;

import java.util.Collection;
import java.util.HashSet;

public class EnumAttributeDefinition extends AttributeDefinition {
    HashSet<String> values = new HashSet<String>();

    public HashSet<String> getValues() {
        return values;
    }

    public EnumAttributeDefinition(String name, boolean isRequired, boolean isVariant, Collection<String> values) {
        super(name, isRequired, isVariant);
        this.values = new HashSet<String>(values);
    }

    // for the JSON deserializer
    private EnumAttributeDefinition() { }
}
