package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.LocalizedString;

final class AttributeDefinitionImpl implements AttributeDefinition {
    private final AttributeType type;
    private final String name;
    private final LocalizedString label;
    private final boolean isRequired;
    private final AttributeConstraint attributeConstraint;
    private final boolean isSearchable;

    AttributeDefinitionImpl(AttributeType type, String name, LocalizedString label, boolean isRequired, AttributeConstraint attributeConstraint, boolean isSearchable) {
        this.type = type;
        this.name = name;
        this.label = label;
        this.isRequired = isRequired;
        this.attributeConstraint = attributeConstraint;
        this.isSearchable = isSearchable;
    }

    @Override
    public AttributeType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalizedString getLabel() {
        return label;
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    @Override
    public AttributeConstraint getAttributeConstraint() {
        return attributeConstraint;
    }

    @Override
    public boolean isSearchable() {
        return isSearchable;
    }
}
