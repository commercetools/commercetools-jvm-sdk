package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Optional;

public class CustomFields extends Base {
    private final Reference<Type> type;
    private final Map<String, JsonNode> fields;

    private CustomFields(final Reference<Type> type, final Map<String, JsonNode> fields) {
        this.type = type;
        this.fields = fields;
    }

    @Nullable
    public JsonNode getField(final String name) {
        return fields.get(name);
    }

    @Nullable
    public <T> T getField(final String name, final TypeReference<T> typeReference) {
        return Optional.ofNullable(getField(name))
                .map(jsonNode -> SphereJsonUtils.readObject(jsonNode, typeReference))
                .orElse(null);
    }

    @Nullable
    public String getFieldAsString(final String name) {
        return getField(name, TypeReferences.stringTypeReference());
    }

    @Nullable
    public Boolean getFieldAsBoolean(final String name) {
        return getField(name, TypeReferences.booleanTypeReference());
    }

    @Nullable
    public LocalizedString getFieldAsLocalizedString(final String name) {
        return getField(name, LocalizedString.typeReference());
    }

    @Nullable
    public String getFieldAsEnumKey(final String name) {
        return getFieldAsString(name);
    }

    @Nullable
    public String getFieldAsLocalizedEnumKey(final String name) {
        return getFieldAsString(name);
    }

    @Nullable
    public Integer getFieldAsInteger(final String name) {
        return getField(name, TypeReferences.integerTypeReference());
    }

    @Nullable
    public Long getFieldAsLong(final String name) {
        return getField(name, TypeReferences.longTypeReference());
    }

    @Nullable
    public Double getFieldAsDouble(final String name) {
        return getField(name, TypeReferences.doubleTypeReference());
    }

    @Nullable
    public MonetaryAmount getFieldAsMoney(final String name) {
        return getField(name, TypeReferences.monetaryAmountTypeReference());
    }

    @Nullable
    public LocalDate getFieldAsDate(final String name) {
        return getField(name, TypeReferences.localDateTypeReference());
    }

    @Nullable
    public ZonedDateTime getFieldAsDateTime(final String name) {
        return getField(name, TypeReferences.zonedDateTimeTypeReference());
    }

    @Nullable
    public LocalTime getFieldAsTime(final String name) {
        return getField(name, TypeReferences.localTimeTypeReference());
    }

    public Reference<Type> getType() {
        return type;
    }
}
