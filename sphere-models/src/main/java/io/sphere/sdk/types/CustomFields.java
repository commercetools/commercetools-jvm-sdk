package io.sphere.sdk.types;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @see Custom
 */
public class CustomFields extends Base {
    private final Reference<Type> type;
    private final Map<String, JsonNode> fields;

    @JsonCreator
    private CustomFields(final Reference<Type> type, final Map<String, JsonNode> fields) {
        this.type = type;
        this.fields = Collections.unmodifiableMap(fields);
    }

    public Map<String, JsonNode> getFieldsJsonMap() {
        return fields;
    }

    @Nullable
    public JsonNode getFieldAsJsonNode(final String name) {
        return fields.get(name);
    }

    @Nullable
    public <T> T getField(final String name, final TypeReference<T> typeReference) {
        try {
            return Optional.ofNullable(getFieldAsJsonNode(name))
                    .map(jsonNode -> SphereJsonUtils.readObject(jsonNode, typeReference))
                    .orElse(null);
        } catch (final RuntimeException e) {
            throw new JsonException(e);
        }
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

    public Set<String> getFieldAsStringSet(final String name) {
        return getField(name, TypeReferences.stringSetTypeReference());
    }
}
