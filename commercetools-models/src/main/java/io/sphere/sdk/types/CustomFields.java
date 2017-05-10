package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Custom fields for object extending {@link Custom}.
 *
 * @see Custom
 */
@JsonDeserialize(as = CustomFieldsImpl.class)
public interface CustomFields {
    Map<String, JsonNode> getFieldsJsonMap();

    @Nullable
    JsonNode getFieldAsJsonNode(String name);

    @Nullable
    <T> T getField(String name, TypeReference<T> typeReference);

    @Nullable
    String getFieldAsString(String name);

    @Nullable
    Boolean getFieldAsBoolean(String name);

    @Nullable
    LocalizedString getFieldAsLocalizedString(String name);

    @Nullable
    String getFieldAsEnumKey(String name);

    @Nullable
    String getFieldAsLocalizedEnumKey(String name);

    @Nullable
    Integer getFieldAsInteger(String name);

    @Nullable
    Long getFieldAsLong(String name);

    @Nullable
    Double getFieldAsDouble(String name);

    @Nullable
    BigDecimal getFieldAsBigDecimal(String name);

    @Nullable
    MonetaryAmount getFieldAsMoney(String name);

    @Nullable
    LocalDate getFieldAsDate(String name);

    @Nullable
    ZonedDateTime getFieldAsDateTime(String name);

    @Nullable
    LocalTime getFieldAsTime(String name);

    Reference<Type> getType();

    Set<String> getFieldAsStringSet(String name);
}
