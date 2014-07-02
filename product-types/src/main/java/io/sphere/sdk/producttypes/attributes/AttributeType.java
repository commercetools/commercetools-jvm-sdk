package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextType.class, name = "text"),
    @JsonSubTypes.Type(value = LocalizedTextType.class, name = "ltext"),
    @JsonSubTypes.Type(value = EnumType.class, name = "enum"),
    @JsonSubTypes.Type(value = LocalizedEnumType.class, name = "lenum")
})
public interface AttributeType {
    String getName();
}
