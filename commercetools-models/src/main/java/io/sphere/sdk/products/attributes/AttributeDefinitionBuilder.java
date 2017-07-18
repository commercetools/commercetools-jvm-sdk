package io.sphere.sdk.products.attributes;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

/**
 * Builder to create an attribute definition for product types.
 *
 * <p>For {@link AttributeType}s use the Javadoc page of {@link AttributeType}s.</p>
 *
 * <p>brief example:</p>
 * {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoIntegrationTest#createBookProductType()}
 * <p>long example:</p>
 * {@include.example io.sphere.sdk.attributestutorial.ProductTypeCreationDemoIntegrationTest#createProductType()}
 *
 *
 */
public final class AttributeDefinitionBuilder extends Base implements Builder<AttributeDefinition> {
    private String name;
    private LocalizedString label;
    @Nullable
    private LocalizedString inputTip;
    private final AttributeType attributeType;

    private AttributeConstraint attributeConstraint = AttributeConstraint.NONE;
    private TextInputHint inputHint = TextInputHint.SINGLE_LINE;
    Boolean isRequired = false;
    Boolean isSearchable = true;

    AttributeDefinitionBuilder(final String name, final LocalizedString label, final AttributeType attributeType) {
        this.name = name;
        this.label = label;
        this.attributeType = attributeType;
    }

    public AttributeDefinitionBuilder attributeConstraint(final AttributeConstraint attributeConstraint) {
        this.attributeConstraint = attributeConstraint;
        return this;
    }

    public AttributeDefinitionBuilder inputHint(final TextInputHint inputHint) {
        this.inputHint = inputHint;
        return this;
    }

    public AttributeDefinitionBuilder label(final LocalizedString label) {
        this.label = label;
        return this;
    }

    public AttributeDefinitionBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public AttributeDefinitionBuilder inputTip(final LocalizedString inputTip) {
        this.inputTip = inputTip;
        return this;
    }

    public AttributeDefinitionBuilder required(final boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    public AttributeDefinitionBuilder isRequired(final boolean isRequired) {
        return required(isRequired);
    }

    public AttributeDefinitionBuilder searchable(final boolean isSearchable) {
        this.isSearchable = isSearchable;
        return this;
    }

    public AttributeDefinitionBuilder isSearchable(final boolean isSearchable) {
        return searchable(isSearchable);
    }

    @Override
    public AttributeDefinition build() {
        return new AttributeDefinitionImpl(attributeType, name, label, isRequired, attributeConstraint, isSearchable, inputHint, inputTip);
    }

    public static AttributeDefinitionBuilder of(final String name, final LocalizedString label, final AttributeType attributeType) {
        return new AttributeDefinitionBuilder(name, label, attributeType);
    }

    public AttributeConstraint getAttributeConstraint() {
        return attributeConstraint;
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public TextInputHint getInputHint() {
        return inputHint;
    }

    @Nullable
    public LocalizedString getInputTip() {
        return inputTip;
    }

    public Boolean isRequired() {
        return isRequired;
    }

    public Boolean isSearchable() {
        return isSearchable;
    }

    public LocalizedString getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }
}
