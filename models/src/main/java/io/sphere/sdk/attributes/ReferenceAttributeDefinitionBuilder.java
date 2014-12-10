package io.sphere.sdk.attributes;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedStrings;

public class ReferenceAttributeDefinitionBuilder extends AttributeDefinitionBuilder<ReferenceAttributeDefinitionBuilder> implements Builder<ReferenceAttributeDefinition> {

    private final ReferenceType referenceType;

    ReferenceAttributeDefinitionBuilder(final String name, final LocalizedStrings label, final ReferenceType referenceType) {
        super(name, label);
        this.referenceType = referenceType;
    }

    public static ReferenceAttributeDefinitionBuilder of(final String name, final LocalizedStrings label, final ReferenceType referenceType) {
        return new ReferenceAttributeDefinitionBuilder(name, label, referenceType);
    }

    @Override
    protected ReferenceAttributeDefinitionBuilder getThis() {
        return this;
    }

    @Override
    public ReferenceAttributeDefinition build() {
        return new ReferenceAttributeDefinition(referenceType, getName(), getLabel(), isRequired(), getAttributeConstraint(), isSearchable());
    }
}
