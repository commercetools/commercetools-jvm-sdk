package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.*;

/**
 * Type of a product attribute.
 *
 * <p>Product attributes are documented <a href="{@docRoot}/io/sphere/sdk/meta/ProductAttributeDocumentation.html">here</a>.</p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StringAttributeType.class, name = "text"),
    @JsonSubTypes.Type(value = LocalizedStringAttributeType.class, name = "ltext"),
    @JsonSubTypes.Type(value = EnumAttributeType.class, name = "enum"),
    @JsonSubTypes.Type(value = LocalizedEnumAttributeType.class, name = "lenum"),
    @JsonSubTypes.Type(value = NumberAttributeType.class, name = "number"),
    @JsonSubTypes.Type(value = MoneyAttributeType.class, name = "money"),
    @JsonSubTypes.Type(value = TimeAttributeType.class, name = "time"),
    @JsonSubTypes.Type(value = DateAttributeType.class, name = "date"),
    @JsonSubTypes.Type(value = DateTimeAttributeType.class, name = "datetime"),
    @JsonSubTypes.Type(value = BooleanAttributeType.class, name = "boolean"),
    @JsonSubTypes.Type(value = SetAttributeType.class, name = "set"),
    @JsonSubTypes.Type(value = ReferenceAttributeType.class, name = "reference"),
    @JsonSubTypes.Type(value = RichReferenceAttributeType.class, name = "reference"),
    @JsonSubTypes.Type(value = NestedAttributeType.class, name = "nested")
})
public interface AttributeType {
}
