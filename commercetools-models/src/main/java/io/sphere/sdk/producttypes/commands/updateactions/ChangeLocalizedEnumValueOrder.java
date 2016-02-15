package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
 * Changes the localized enum value order.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeLocalizedEnumValueOrder()}
 */
public final class ChangeLocalizedEnumValueOrder extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final List<LocalizedEnumValue> values;

    private ChangeLocalizedEnumValueOrder(final String attributeName, final List<LocalizedEnumValue> values) {
        super("changeLocalizedEnumValueOrder");
        this.attributeName = attributeName;
        this.values = values;
    }

    public static ChangeLocalizedEnumValueOrder of(final String attributeName, final List<LocalizedEnumValue> values) {
        return new ChangeLocalizedEnumValueOrder(attributeName, values);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }
}
