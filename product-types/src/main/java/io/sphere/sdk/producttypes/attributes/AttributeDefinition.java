package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;


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
 * </ul>
 *
 */
@JsonDeserialize(using = AttributeDefinitionDeserializer.class)
public interface AttributeDefinition {
    AttributeType getAttributeType();

    String getName();

    LocalizedString getLabel();

    boolean getIsRequired();

    AttributeConstraint getAttributeConstraint();

    boolean getIsSearchable();
}
