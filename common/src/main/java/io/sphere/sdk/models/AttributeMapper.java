package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

import static io.sphere.sdk.models.JavaTypeReferences.*;

/**
 *
 * @param <T> the result type of the attribute, e.g., {@link io.sphere.sdk.models.LocalizedString}
 *
 */
public interface AttributeMapper<T> {
    public static AttributeMapper<Boolean> ofBoolean() {
        return AttributeMapper.of(booleanTypeReference());
    }

    public static AttributeMapper<Double> ofDouble() {
        return AttributeMapper.of(doubleTypeReference());
    }

    public static AttributeMapper<Double> ofNumber() {
        return ofDouble();
    }

    public static AttributeMapper<Long> ofLong() {
        return AttributeMapper.of(longTypeReference());
    }


    public static AttributeMapper<String> ofText() {
        return AttributeMapper.of(stringTypeReference());
    }

    /**
     * Alias for {@link AttributeMapper#ofText()}.
     *
     * @return attribute mapper
     */
    public static AttributeMapper<String> ofString() {
        return ofText();
    }

    public static AttributeMapper<LocalizedString> ofLocalizedString() {
        return AttributeMapper.of(LocalizedString.typeReference());
    }

    /**
     * Alias for {@link AttributeMapper#ofEnum()}.
     *
     * @return attribute mapper
     */
    public static AttributeMapper<PlainEnumValue> ofPlainEnumValue() {
        return ofEnum();
    }

    public static AttributeMapper<PlainEnumValue> ofEnum() {
        return AttributeMapper.of(PlainEnumValue.typeReference());
    }

    /**
     * Alias for {@link AttributeMapper#ofLocalizedEnum()}.
     *
     * @return attribute mapper
     */
    public static AttributeMapper<LocalizedEnumValue> ofLocalizedEnumValue() {
        return ofLocalizedEnum();
    }

    public static AttributeMapper<LocalizedEnumValue> ofLocalizedEnum() {
        return AttributeMapper.of(LocalizedEnumValue.typeReference());
    }

    public static AttributeMapper<Money> ofMoney() {
        return AttributeMapper.of(Money.typeReference());
    }

    public static AttributeMapper<LocalDate> ofDate() {
        return AttributeMapper.of(localDateTypeReference());
    }

    /**
     * Alias for {@link AttributeMapper#ofDate()}.
     *
     * @return attribute mapper
     */
    public static AttributeMapper<LocalDate> ofLocalDate() {
        return ofDate();
    }
    
    public static AttributeMapper<LocalTime> ofTime() {
        return AttributeMapper.of(localTimeTypeReference());
    }

    /**
     * Alias for {@link AttributeMapper#ofTime()}.
     *
     * @return attribute mapper
     */
    public static AttributeMapper<LocalTime> ofLocalTime() {
        return ofTime();
    }


    public static AttributeMapper<Instant> ofDateTime() {
        return AttributeMapper.of(instantTypeReference());
    }

    /**
     * Alias for {@link AttributeMapper#ofDateTime()}.
     *
     * @return attribute mapper
     */
    public static AttributeMapper<Instant> ofInstant() {
        return ofDateTime();
    }

    T parse(final JsonNode value);

    public static <T> AttributeMapper<T> of(final TypeReference<T> typeReference) {
        return new AttributeMapperImpl<>(typeReference);
    }
}
