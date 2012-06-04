package sphere.model.products;

public class DateAttributeDefinition extends AttributeDefinition {

    public DateAttributeDefinition(String name, boolean isRequired, boolean isVariant) {
        super(name, isRequired, isVariant);
    }

    // for the JSON deserializer
    private DateAttributeDefinition() { }
}
