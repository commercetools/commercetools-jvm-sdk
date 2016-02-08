package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @see Custom
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BooleanFieldType.class, name = "Boolean"),
        @JsonSubTypes.Type(value = StringFieldType.class, name = "String"),
        @JsonSubTypes.Type(value = LocalizedStringFieldType.class, name = "LocalizedString"),
        @JsonSubTypes.Type(value = EnumFieldType.class, name = "Enum"),
        @JsonSubTypes.Type(value = LocalizedEnumFieldType.class, name = "LocalizedEnum"),
        @JsonSubTypes.Type(value = NumberFieldType.class, name = "Number"),
        @JsonSubTypes.Type(value = MoneyFieldType.class, name = "Money"),
        @JsonSubTypes.Type(value = DateFieldType.class, name = "Date"),
        @JsonSubTypes.Type(value = TimeFieldType.class, name = "Time"),
        @JsonSubTypes.Type(value = DateTimeFieldType.class, name = "DateTime"),
        @JsonSubTypes.Type(value = ReferenceFieldType.class, name = "Reference"),
        @JsonSubTypes.Type(value = SetFieldType.class, name = "Set")
})
public interface FieldType {
}
