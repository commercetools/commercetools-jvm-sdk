package io.sphere.sdk.products.attributes;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;

import javax.annotation.Nullable;

public final class AttributeDefinitionDraftBuilder extends AttributeDefinitionDraftBuilderBase<AttributeDefinitionDraftBuilder> {

    AttributeDefinitionDraftBuilder(@Nullable final AttributeConstraint attributeConstraint,
                                    final AttributeType attributeType, final TextInputHint inputHint,
                                    @Nullable final LocalizedString inputTip, final LocalizedString label, final String name,
                                    final Boolean required, final Boolean searchable) {
        super(attributeConstraint, attributeType, inputHint, inputTip, label, name, required, searchable);
    }

    /**
     * Creates a new object initialized with the fields of the template parameter.
     *
     * @param template the template
     * @return a new object initialized from the template
     */
    public static AttributeDefinitionDraftBuilder of(final AttributeDefinition template) {
        return new AttributeDefinitionDraftBuilder(template.getAttributeConstraint(), template.getAttributeType(), template.getInputHint(), template.getInputTip(), template.getLabel(), template.getName(), template.isRequired(), template.isSearchable());
    }

}
