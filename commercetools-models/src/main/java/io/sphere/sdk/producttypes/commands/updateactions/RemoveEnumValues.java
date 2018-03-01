package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.products.attributes.EnumAttributeType;
import io.sphere.sdk.products.attributes.LocalizedEnumAttributeType;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Removes enum values from an attribute definition of an {@link EnumAttributeType}, a {@link LocalizedEnumAttributeType}
 * or a {@link io.sphere.sdk.products.attributes.SetAttributeType} of these types.
 *
 * All attributes of all products using those enum keys will also be removed in an eventually consistent way.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#removeEnumValues()}
 *
 */
public final class RemoveEnumValues extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final List<String> keys;

    private RemoveEnumValues(final String attributeName, final List<String> keys) {
        super("removeEnumValues");
        this.attributeName = attributeName;
        this.keys = keys;
    }

    public static RemoveEnumValues of(final String attributeName, final List<String> keys) {
        return new RemoveEnumValues(attributeName, keys);
    }

    public static RemoveEnumValues of(final String attributeName, final String key) {
        return of(attributeName, Collections.singletonList(key));
    }

    public static RemoveEnumValues ofLocalizedEnumValue(final String attributeName, final List<LocalizedEnumValue> localizedEnumValues) {
        return new RemoveEnumValues(attributeName, localizedEnumValues.stream().map(LocalizedEnumValue::getKey).collect(Collectors.toList()));
    }

    public static RemoveEnumValues ofLocalizedEnumValue(final String attributeName, final LocalizedEnumValue localizedEnumValue) {
        return ofLocalizedEnumValue(attributeName, Collections.singletonList(localizedEnumValue));
    }

    public static RemoveEnumValues ofEnumValue(final String attributeName, final List<EnumValue> enumValues) {
        return new RemoveEnumValues(attributeName, enumValues.stream().map(EnumValue::getKey).collect(Collectors.toList()));
    }

    public static RemoveEnumValues ofEnumValue(final String attributeName, final EnumValue enumValue) {
        return ofEnumValue(attributeName, Collections.singletonList(enumValue));
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<String> getKeys() {
        return keys;
    }
}
