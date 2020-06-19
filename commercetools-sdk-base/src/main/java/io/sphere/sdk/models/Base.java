package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * A base class which implements {@link Object#toString()}, {@link Object#hashCode()} and {@link Object#equals(Object)} by reflection.
 */
public abstract class Base {

    private final Map<String, JsonNode> additionalFields = new HashMap<>();

    @JsonIgnore
    public Map<String, JsonNode> additionalFields() {
        return additionalFields;
    }

    @JsonAnySetter
    private void setAdditionalFields(String name, JsonNode value) {
        additionalFields.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, SdkDefaults.TO_STRING_STYLE)
                //important to not log passwords
                .setExcludeFieldNames("password", "newPassword", "currentPassword", "oldPassword", "access_token", "accessToken")
                .build();
    }
}
