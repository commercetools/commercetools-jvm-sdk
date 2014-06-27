package io.sphere.sdk.producttypes.attributes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;

@JsonDeserialize(as=AttributeDefinitionBase.class)
public interface AttributeDefinition {
    AttributeType getAttributeType();

    String getName();

    LocalizedString getLabel();

    boolean getIsRequired();

    AttributeConstraint getAttributeConstraint();

    boolean getIsSearchable();
}
