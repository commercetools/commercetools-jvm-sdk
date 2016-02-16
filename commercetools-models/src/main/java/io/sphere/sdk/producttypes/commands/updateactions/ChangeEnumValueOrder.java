package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 * Changes the enum value order.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeEnumValueOrder()}
 */
public final class ChangeEnumValueOrder extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final List<EnumValue> values;

    private ChangeEnumValueOrder(final String attributeName, final List<EnumValue> values) {
        super("changePlainEnumValueOrder");
        this.attributeName = attributeName;
        this.values = values;
    }

    public static ChangeEnumValueOrder of(final String attributeName, final List<EnumValue> values) {
        return new ChangeEnumValueOrder(attributeName, values);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<EnumValue> getValues() {
        return values;
    }
}
