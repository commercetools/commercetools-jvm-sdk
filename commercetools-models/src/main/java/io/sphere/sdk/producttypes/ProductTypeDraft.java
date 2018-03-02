package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraft;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraftBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand
 */
@JsonDeserialize(as = ProductTypeDraftDsl.class)
public interface ProductTypeDraft extends WithKey {
    String getName();

    String getDescription();

    List<AttributeDefinitionDraft> getAttributes();

    @Nullable
    String getKey();

    /**
     * Creates a input object to create a {@link ProductType}.
     *
     * @param key         unique key to identify the created {@link ProductType}
     * @param name        the name of the product type, the platform does not check that the name will be unique so it is best practice to check if a product type of this name already exists
     * @param description description of the product type
     * @param attributes  definitions of attributes for the product type
     * @return draft for a product type
     */
    @Deprecated
    static ProductTypeDraft of(@Nullable final String key, final String name, final String description, final List<AttributeDefinition> attributes) {
        return ofAttributeDefinitionDrafts(key, name, description, copyAttributes(attributes));
    }

    static ProductTypeDraft ofAttributeDefinitionDrafts(@Nullable final String key, final String name, final String description, final List<AttributeDefinitionDraft> attributes) {
        return ProductTypeDraftDsl.of(key, name, description, attributes);
    }

    static List<AttributeDefinitionDraft> copyAttributes(final List<AttributeDefinition> templates) {
        return templates == null ? null : templates.stream().map(template -> AttributeDefinitionDraftBuilder.of(template).build()).collect(Collectors.toList());
    }
}
