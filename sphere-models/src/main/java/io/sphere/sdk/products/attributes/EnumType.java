package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.EnumValue;

import java.util.List;

import static io.sphere.sdk.utils.ListUtils.listOf;

public class EnumType extends AttributeTypeBase {
    private final List<EnumValue> values;

    @JsonCreator
    private EnumType(final List<EnumValue> values) {
        this.values = values;
    }

    public List<EnumValue> getValues() {
        return values;
    }

    @JsonIgnore
    public static EnumType of(final EnumValue mandatoryValue, final EnumValue... moreValues) {
        return of(listOf(mandatoryValue, moreValues));
    }

    @JsonIgnore
    public static EnumType of(final List<EnumValue> values) {
        return new EnumType(values);
    }
}
