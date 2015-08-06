package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.LocalizedEnumValue;

import java.util.List;

import static io.sphere.sdk.utils.ListUtils.listOf;

public class LocalizedEnumType extends AttributeTypeBase {
    private final List<LocalizedEnumValue> values;

    @JsonCreator
    private LocalizedEnumType(final List<LocalizedEnumValue> values) {
        this.values = values;
    }

    public List<LocalizedEnumValue> getValues() {
        return values;
    }

    @JsonIgnore
    public static LocalizedEnumType of(final LocalizedEnumValue mandatoryValue, final LocalizedEnumValue ... moreValues) {
        final List<LocalizedEnumValue> localizedEnumValues = listOf(mandatoryValue, moreValues);
        return of(localizedEnumValues);
    }

    @JsonIgnore
    public static LocalizedEnumType of(final List<LocalizedEnumValue> values) {
        return new LocalizedEnumType(values);
    }
}
