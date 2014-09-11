package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextType.class, name = "text"),
    @JsonSubTypes.Type(value = LocalizedTextType.class, name = "ltext"),
    @JsonSubTypes.Type(value = EnumType.class, name = "enum"),
    @JsonSubTypes.Type(value = LocalizedEnumType.class, name = "lenum"),
    @JsonSubTypes.Type(value = NumberType.class, name = "number"),
    @JsonSubTypes.Type(value = MoneyType.class, name = "money"),
    @JsonSubTypes.Type(value = TimeType.class, name = "time"),
    @JsonSubTypes.Type(value = DateType.class, name = "date"),
    @JsonSubTypes.Type(value = DateTimeType.class, name = "datetime"),
    @JsonSubTypes.Type(value = BooleanType.class, name = "boolean"),
    @JsonSubTypes.Type(value = SetType.class, name = "set")
})
public interface AttributeType {
    String getName();
}
