package de.commercetools.sphere.client.model.products;

import org.codehaus.jackson.annotate.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextAttributeDefinition.class, name = "text"),
    @JsonSubTypes.Type(value = EnumAttributeDefinition.class, name = "enum"),
    @JsonSubTypes.Type(value = NumberAttributeDefinition.class, name = "number"),
    @JsonSubTypes.Type(value = MoneyAttributeDefinition.class, name = "money"),
    @JsonSubTypes.Type(value = DateAttributeDefinition.class, name = "date"),
    @JsonSubTypes.Type(value = TimeAttributeDefinition.class, name = "time"),
    @JsonSubTypes.Type(value = DateTimeAttributeDefinition.class, name = "datetime")
})
public abstract class AttributeDefinition {
    protected String name;
    @JsonProperty(value="isRequired")
    protected boolean isRequired;
    @JsonProperty(value="isVariant")
    protected boolean isVariant;

    public String getName() { return name; }
    public boolean isRequired() { return isRequired; }
    public boolean isVariant() { return isVariant; }

    protected AttributeDefinition(String name, boolean isRequired, boolean isVariant) {
        this.name = name;
        this.isRequired = isRequired;
        this.isVariant = isVariant;
    }

    // for JSON deserializer
    protected AttributeDefinition() { }
}
