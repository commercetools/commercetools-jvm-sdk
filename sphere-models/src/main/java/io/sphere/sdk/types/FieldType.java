package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @see Custom
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BooleanType.class, name = "Boolean"),
        @JsonSubTypes.Type(value = StringType.class, name = "String"),
        @JsonSubTypes.Type(value = LocalizedStringType.class, name = "LocalizedString"),
        @JsonSubTypes.Type(value = EnumType.class, name = "Enum"),
        @JsonSubTypes.Type(value = LocalizedEnumType.class, name = "LocalizedEnum"),
        @JsonSubTypes.Type(value = NumberType.class, name = "Number"),
        @JsonSubTypes.Type(value = MoneyType.class, name = "Money"),
        @JsonSubTypes.Type(value = DateType.class, name = "Date"),
        @JsonSubTypes.Type(value = TimeType.class, name = "Time"),
        @JsonSubTypes.Type(value = DateTimeType.class, name = "DateTime"),
        @JsonSubTypes.Type(value = ReferenceType.class, name = "Reference"),
        @JsonSubTypes.Type(value = SetType.class, name = "Set")
})
public interface FieldType {
}
