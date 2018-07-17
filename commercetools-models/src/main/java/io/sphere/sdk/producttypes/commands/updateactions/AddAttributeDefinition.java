package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraft;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraftBuilder;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Adds an attribute definition to a product type.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#addAttributeDefinition()}
 *
 * @see ProductType#getAttributes()
 * @see io.sphere.sdk.products.ProductVariant#getAttributes()
 */
public final class AddAttributeDefinition extends UpdateActionImpl<ProductType> {
    private final AttributeDefinitionDraft attribute;

    private AddAttributeDefinition(final AttributeDefinitionDraft attributeDefinition) {
        super("addAttributeDefinition");
        this.attribute = attributeDefinition;
    }

    /**
     * @param attributeDefinition
     * @return AddAttributeDefinition update action
     * @deprecated use {@link AddAttributeDefinition#of(AttributeDefinitionDraft)} instead
     */
    public static AddAttributeDefinition of(final AttributeDefinition attributeDefinition) {
        return new AddAttributeDefinition(AttributeDefinitionDraftBuilder.of(attributeDefinition).build());
    }

    public static AddAttributeDefinition of(final AttributeDefinitionDraft attributeDefinitionDraft) {
        return new AddAttributeDefinition(attributeDefinitionDraft);
    }

    public AttributeDefinitionDraft getAttribute() {
        return attribute;
    }
}
