package io.sphere.sdk.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedStrings;


/**
 * Builders:
 *
 * <ul>
 *     <li>{@link BooleanAttributeDefinitionBuilder}</li>
 *     <li>{@link DateAttributeDefinitionBuilder}</li>
 *     <li>{@link DateTimeAttributeDefinitionBuilder}</li>
 *     <li>{@link EnumAttributeDefinitionBuilder}</li>
 *     <li>{@link LocalizedEnumAttributeDefinitionBuilder}</li>
 *     <li>{@link LocalizedTextAttributeDefinitionBuilder}</li>
 *     <li>{@link MoneyAttributeDefinitionBuilder}</li>
 *     <li>{@link NumberAttributeDefinitionBuilder}</li>
 *     <li>{@link SetAttributeDefinitionBuilder}</li>
 *     <li>{@link TextAttributeDefinitionBuilder}</li>
 *     <li>{@link TimeAttributeDefinitionBuilder}</li>
 *     <li>{@link ReferenceAttributeDefinitionBuilder}</li>
 * </ul>
 *
 */
@JsonDeserialize(using = AttributeDefinitionDeserializer.class)
public interface AttributeDefinition {
    AttributeType getAttributeType();

    String getName();

    LocalizedStrings getLabel();

    boolean getIsRequired();

    AttributeConstraint getAttributeConstraint();

    boolean getIsSearchable();
}
