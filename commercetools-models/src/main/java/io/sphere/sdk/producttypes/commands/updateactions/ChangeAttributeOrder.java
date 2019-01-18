package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 * @deprecated Please use {@link ChangeAttributeOrderByName}.
 * Changes the attribute order.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeAttributeOrder()}
 */
@Deprecated
public final class ChangeAttributeOrder extends UpdateActionImpl<ProductType> {
    private final List<AttributeDefinition> attributes;

    private ChangeAttributeOrder(final List<AttributeDefinition> attributes) {
        super("changeAttributeOrder");
        this.attributes = attributes;
    }

    public static ChangeAttributeOrder of(final List<AttributeDefinition> attributes) {
        return new ChangeAttributeOrder(attributes);
    }

    public List<AttributeDefinition> getAttributes() {
        return attributes;
    }
}
