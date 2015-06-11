package io.sphere.sdk.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.PlainEnumValue;

import java.util.List;

import static io.sphere.sdk.utils.ListUtils.listOf;

public class EnumType extends AttributeTypeBase {
    private final List<PlainEnumValue> values;

    @JsonCreator
    private EnumType(final List<PlainEnumValue> values) {
        this.values = values;
    }

    public List<PlainEnumValue> getValues() {
        return values;
    }

    @JsonIgnore
    public static EnumType of(final PlainEnumValue mandatoryValue, final PlainEnumValue ... moreValues) {
        return of(listOf(mandatoryValue, moreValues));
    }

    @JsonIgnore
    public static EnumType of(final List<PlainEnumValue> values) {
        return new EnumType(values);
    }
}
