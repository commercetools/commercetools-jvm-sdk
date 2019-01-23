package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Single product attribute which contains a name (key semantic) and a value.
 *
 * <p>Product attributes are documented <a href="{@docRoot}/io/sphere/sdk/meta/ProductAttributeDocumentation.html">here</a>.</p>
 */
@JsonDeserialize(as = AttributeImpl.class)
public interface Attribute {
    String getName();

    <T> T getValue(final AttributeAccess<T> access);

    static Attribute of(final String name, final JsonNode jsonNode) {
        return new AttributeImpl(name, jsonNode);
    }

    static <T> Attribute of(final String name, final AttributeAccess<T> access, final T value) {
        return of(access.ofName(name), value);
    }

    static <T> Attribute of(final NamedAttributeAccess<T> namedAttributeAccess, final T value) {
        final String name = namedAttributeAccess.getName();
        final JsonNode jsonNode = ReferenceInternalObjectMapper.getInstance().valueToTree(value);
        return of(name, jsonNode);
    }

    @JsonIgnore
    default Boolean getValueAsBoolean() {
        return getValue(AttributeAccess.ofBoolean());
    }

    @JsonIgnore
    default Set<Boolean> getValueAsBooleanSet() {
        return getValue(AttributeAccess.ofBooleanSet());
    }

    @JsonIgnore
    default String getValueAsString() {
        return getValue(AttributeAccess.ofString());
    }

    @JsonIgnore
    default Set<String> getValueAsStringSet() {
        return getValue(AttributeAccess.ofStringSet());
    }

    @JsonIgnore
    default LocalizedString getValueAsLocalizedString() {
        return getValue(AttributeAccess.ofLocalizedString());
    }

    @JsonIgnore
    default Set<LocalizedString> getValueAsLocalizedStringSet() {
        return getValue(AttributeAccess.ofLocalizedStringSet());
    }

    @JsonIgnore
    default EnumValue getValueAsEnumValue() {
        return getValue(AttributeAccess.ofEnumValue());
    }

    @JsonIgnore
    default Set<EnumValue> getValueAsEnumValueSet() {
        return getValue(AttributeAccess.ofEnumValueSet());
    }

    @JsonIgnore
    default LocalizedEnumValue getValueAsLocalizedEnumValue() {
        return getValue(AttributeAccess.ofLocalizedEnumValue());
    }

    @JsonIgnore
    default Set<LocalizedEnumValue> getValueAsLocalizedEnumValueSet() {
        return getValue(AttributeAccess.ofLocalizedEnumValueSet());
    }

    @JsonIgnore
    default Double getValueAsDouble() {
        return getValue(AttributeAccess.ofDouble());
    }

    @JsonIgnore
    default Set<Double> getValueAsDoubleSet() {
        return getValue(AttributeAccess.ofDoubleSet());
    }

    @JsonIgnore
    default Integer getValueAsInteger() {
        return getValue(AttributeAccess.ofInteger());
    }

    @JsonIgnore
    default Set<Integer> getValueAsIntegerSet() {
        return getValue(AttributeAccess.ofIntegerSet());
    }

    @JsonIgnore
    default Long getValueAsLong() {
        return getValue(AttributeAccess.ofLong());
    }

    @JsonIgnore
    default Set<Long> getValueAsLongSet() {
        return getValue(AttributeAccess.ofLongSet());
    }

    @JsonIgnore
    default MonetaryAmount getValueAsMoney() {
        return getValue(AttributeAccess.ofMoney());
    }

    @JsonIgnore
    default Set<MonetaryAmount> getValueAsMoneySet() {
        return getValue(AttributeAccess.ofMoneySet());
    }

    @JsonIgnore
    default LocalDate getValueAsLocalDate() {
        return getValue(AttributeAccess.ofLocalDate());
    }

    @JsonIgnore
    default Set<LocalDate> getValueAsLocalDateSet() {
        return getValue(AttributeAccess.ofLocalDateSet());
    }

    @JsonIgnore
    default LocalTime getValueAsLocalTime() {
        return getValue(AttributeAccess.ofLocalTime());
    }

    @JsonIgnore
    default Set<LocalTime> getValueAsLocalTimeSet() {
        return getValue(AttributeAccess.ofLocalTimeSet());
    }

    @JsonIgnore
    default ZonedDateTime getValueAsDateTime() {
        return getValue(AttributeAccess.ofZonedDateTime());
    }

    @JsonIgnore
    default Set<ZonedDateTime> getValueAsDateTimeSet() {
        return getValue(AttributeAccess.ofZonedDateTimeSet());
    }

    @JsonIgnore
    default JsonNode getValueAsJsonNode() {
        return getValue(AttributeAccess.ofJsonNode());
    }
}
